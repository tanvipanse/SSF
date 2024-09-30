import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.net.http.*;
import java.net.URI;


import java.io.IOException;
import java.util.*;


public class Receiver {
    public static class SsfEventSets {
        private Map<String, String> sets;

        public Map<String, String> getSets() {
            return sets;
        }

        public void setSets(Map<String, String> sets) {
            this.sets = sets;
        }
    }

    //DO ALL THE ERROR CHECKS HERE
    //func (receiver *SsfReceiverImplementation) PollEvents() ([]events.SsfEvent, error)
    public List<SsfEvent> pollEvents() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper(); //use this for marshalling #jsonvibes

        PollTransmitterRequest pollRequest = new PollTransmitterRequest(Collections.emptyList(), 10, true);

        String requestBody = objectMapper.writeValueAsString(pollRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create("https://ssf.caep.dev/ssf/streams/poll")) //transmitter poll url goes here
                .setHeader("Authorization", "Bearer 5f3be6b7-29b2-4261-94ba-5faa8cba2b4e" /* + access token */) //access token goes here as a variable
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String respStripped = response.body().substring(8);
        System.out.println(respStripped); //debugging
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<>() {};
        HashMap<String,Object> body;
        try {
            body = objectMapper.readValue(new ByteArrayInputStream(respStripped.getBytes("UTF-8")), typeRef);
        } catch (Exception e) {
            throw new Exception("unable to unmarshal");
        }

        if (response.statusCode() != 200 && response.statusCode() != 202) {
            throw new Exception("status code failure");
        }

        SsfEventSets ssfEventSets;
        ObjectMapper objMapper = new ObjectMapper();

        System.out.println("Body: " + body);
        try {
            String jsonString = objMapper.writeValueAsString(body);
            System.out.println(jsonString);
            ssfEventSets = objMapper.readValue(jsonString, SsfEventSets.class);
        } catch (Exception e) {
            throw new Exception("unable to unmarshal");
        }

        if (ssfEventSets.sets.size() > 0) {
            try {
                System.out.println("hello2"); //debugging
                acknowledgeEvents(ssfEventSets.sets);
            } catch (Exception e) {
                throw new Exception("unable to acknowledge events");
            }
        }

        System.out.println("hello"); //debugging
        return parseSsfEventSets(ssfEventSets.sets);



//        defer response.Body.Close()
//
//	body, err := io.ReadAll(response.Body)
//	if err != nil {
//		return []events.SsfEvent{}, err
//	}
//
//	if response.StatusCode != 200 && response.StatusCode != 202 {
//		return []events.SsfEvent{}, err
//	}
//
//	type SsfEventSets struct {
//		Sets map[string]string `json:"sets"`
//	}
//
//	var ssfEventsSets SsfEventSets
//	err = json.Unmarshal(body, &ssfEventsSets)
//	if err != nil {
//		return []events.SsfEvent{}, nil
//	}
//
//	if len(ssfEventsSets.Sets) > 0 {
//		err = acknowledgeEvents(&ssfEventsSets.Sets, receiver)
//		if err != nil {
//			return []events.SsfEvent{}, nil
//		}
//	}
//	events, err := parseSsfEventSets(&ssfEventsSets.Sets)
//	return events, err
    }

    public static void acknowledgeEvents(Map<String, String> sets /*, SsfReceiver Implementation */) throws Exception {
        String[] ackList = new String[sets.size()];
        int i = 0;
        for (String jti : sets.keySet()) {
            ackList[i] = jti;
            i++;
        }

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper(); //use this for marshalling #jsonvibes

        PollTransmitterRequest pollRequest = new PollTransmitterRequest(Collections.emptyList(), 0, true); /* change first param */

        String requestBody = objectMapper.writeValueAsString(pollRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create("https://ssf.caep.dev/ssf/streams/poll")) //transmitter poll url goes here
                .setHeader("Authorization", "Bearer 5f3be6b7-29b2-4261-94ba-5faa8cba2b4e" /* + access token */) //access token goes here as a variable
                .header("Content-Type", "application/json")
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new Exception("event not acknowledged");
        }
    }

    public List<SsfEvent> parseSsfEventSets(Map<String, String> sets) throws Exception {
        List<SsfEvent> ssfEventsList = new ArrayList<>();
        Map<String, Object> ssfEvents;

        for (String set : sets.keySet()) {
            DecodedJWT decodedJWT;
            try {
                decodedJWT = JWT.decode(set);
            } catch (JWTDecodeException e) {
                throw new Exception("decode failed");
            }

            Map<String, Claim> claims = decodedJWT.getClaims();

            if (claims == null) {
                throw new Exception("Can't get JWT Claims");
            }

            if (claims.get("events") instanceof Map) {
                System.out.println(claims.get("events")); //debugging
                ssfEvents = claims.get("events").asMap();
            } else {
                throw new Exception("unable to parse events");
            }

            Map<String, Object> claimsObj = convertClaimsToObjMap(claims);

            for (String eventType : ssfEvents.keySet()) {
                Object eventSubject = ssfEvents.get(eventType);
                SsfEvent ssfEvent;

                try {
                    ssfEvent = SsfEventClass.eventStructFromStruct(eventType, eventSubject, claimsObj);
                } catch (Exception e) {
                    return Collections.emptyList();
                }
                ssfEventsList.add(ssfEvent);
            }
        }
        return ssfEventsList;
    }

    private static Map<String, Object> convertClaimsToObjMap(Map<String, Claim> claims) {
        Map<String, Object> claimsMap = new HashMap<>();

        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            String key = entry.getKey();
            Claim claim = entry.getValue();

            if (claim.isNull()) {
                claimsMap.put(key, null);
            } else if (claim.asString() != null) {
                claimsMap.put(key, claim.asString());
            } else if (claim.asInt() != null) {
                claimsMap.put(key, claim.asInt());
            } else if (claim.asBoolean() != null) {
                claimsMap.put(key, claim.asBoolean());
            } else if (claim.asList(String.class) != null) {
                claimsMap.put(key, claim.asList(String.class));
            }
        }

        return claimsMap;
    }




}
