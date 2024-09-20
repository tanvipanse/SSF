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
    //maybe just make this whole main method a function inside this class
    public static void main(String[] args) throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//        ObjectMapper objectMapper = new ObjectMapper(); //use this for marshalling #jsonvibes
//
//        PollTransmitterRequest pollRequest = new PollTransmitterRequest(Collections.emptyList(), 10, true);
//        /* make a class that represents a struct to define the pollRequest */
//
//        String requestBody = objectMapper.writeValueAsString(pollRequest);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                .uri(URI.create("https://ssf.caep.dev/ssf/streams/poll")) //transmitter poll url goes here
//                .setHeader("Authorization", "Bearer 5f3be6b7-29b2-4261-94ba-5faa8cba2b4e" /* + access token */) //access token goes here as a variable
//                .header("Content-Type", "application/json")
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        //System.out.println(requestBody);
//        //System.out.println(request);
//        //System.out.println(request.headers());
//        //System.out.println(response.statusCode());
//        System.out.println(response.body());
//        //System.out.println(response.body());
//        //HashSet<String> mewo = response.body();
//        System.out.println(response.body().length());
//        String respStripped = response.body().substring(8); //STRIPPING OUT THE "sets" PORTION this should work for all  cases
//        //String respStripped = "{\"" + response.body().substring(10, 702); //STRIPPING OUT THE "sets" PORTION bruh this doesn't work unless its a super specific case
//        System.out.println(respStripped);
//        String test = "{\"sets\": {\"name\": \"Tanvi\"}}";
//
//
//        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<>() {};
//
//        HashMap<String,Object> ssfEventSets = objectMapper.readValue(new ByteArrayInputStream(respStripped.getBytes("UTF-8")), typeRef);
//        //HashMap<Object, Object> map2 = objectMapper.readValue(new ByteArrayInputStream((map.get("sets").getBytes("UTF-8")), typeRef);
//        System.out.println("map keys: " + ssfEventSets.keySet() + "\nmap values: " + ssfEventSets.values());
//        //HashMap<String,Object> ssfEventsSets = objectMapper.readValue(new ByteArrayInputStream(map.values().getBytes("UTF-8")), typeRef);
////        HashMap<String,Object> test1 = objectMapper.readValue(new ByteArrayInputStream(test.getBytes("UTF-8")), typeRef);
////        System.out.println(test1.values());
////        System.out.println(test1.get("name"));
//        //System.out.println(ssfEventsSets.values());
//        System.out.println(ssfEventSets.size());

        //figure this part out
        //something to do with BodyHandlers.ofString

//        byte[] body = Files.readAllBytes(Paths.get(response.body())); //could also be a string --> String body = Files.readString(Paths.get(response.body()));
//
//        String[] events = objectMapper.readValue(body, String[].class);
//
//        System.out.println(events);\

        //acknowledge events
//        HashMap<String, String> ssfEventSetsNew = (HashMap) ssfEventSets;
//        acknowledgeEvents(ssfEventSetsNew);


        /*
        //IF ssfEventsSets.size() > 0
        String[] ackList = new String[ssfEventSets.size()];
        int i = 0;
        for (String jti : ssfEventSets.keySet()) {
            ackList[i] = jti;
            i++;
        }



        HttpClient client1 = HttpClient.newHttpClient();
        ObjectMapper objectMapper1 = new ObjectMapper(); //use this for marshalling #jsonvibes

        PollTransmitterRequest pollRequest1 = new PollTransmitterRequest(Collections.emptyList(), 0, true); // change first param

        String requestBody1 = objectMapper1.writeValueAsString(pollRequest1);
*/
//        HttpRequest request1 = HttpRequest.newBuilder()
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody1))
//                .uri(URI.create("https://ssf.caep.dev/ssf/streams/poll")) //transmitter poll url goes here
//                .setHeader("Authorization", "Bearer 5f3be6b7-29b2-4261-94ba-5faa8cba2b4e" /* + access token */) //access token goes here as a variable
//                .header("Content-Type", "application/json")
//                .build();
//
//        HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
//

        //figure out how to parse this one type of event that is being sent --> session revoked

        //for each set in ssfEventsSets



//        for(Object set : ssfEventSets.values()){
//            DecodedJWT decodedJWT = JWT.decode(set.toString());
//            System.out.println("decoded JWT: " + decodedJWT);
//            //System.out.println("decodedJWT: " + decodedJWT.getToken());
//            System.out.println("CLAIMS: " + decodedJWT.getClaims());
//
//            //System.out.println("subject" + decodedJWT.getClaim("events").getClaim("subject"));
//            //System.out.println(decodedJWT.getSubject());
//            System.out.println("stuff: " + decodedJWT.getClaims().get("events").asMap());
//            System.out.println("aud: " + decodedJWT.getClaim("aud"));
//            System.out.println("class: " + decodedJWT.getClaims().getClass());
//            System.out.println("sub id: " + decodedJWT.getClaim("sub_id"));
//            System.out.println("events: " + decodedJWT.getClaim("events"));
//            Map<String, Object> claim_map = decodedJWT.getClaim("events").asMap();
//            System.out.println("claim map: " + claim_map);
//            System.out.println("key: " + claim_map.keySet());
//            System.out.println("val: " + claim_map.values());
//            Object[] array = claim_map.keySet().toArray();
//            String value = array[0].toString();
//
//
//            SsfEventClass ssfEvent1 = new SsfEventClass();
//            ssfEvent1.eventStructFromStruct(value, claim_map.values(), claim_map);
//            //System.out.println(claim_map.values().toString());
//
////            Map<String, String> map = new HashMap<>();
////            StringTokenizer tokenizer = new StringTokenizer(claim_map.values().toString(), ", ");
////
////            while (tokenizer.hasMoreTokens()) {
////                String token = tokenizer.nextToken();
////                String[] keyValue = token.split("=");
////                map.put(keyValue[0], keyValue[1]);
////            }
////
////            System.out.println("map keys: " + map.keySet());
////            System.out.println("map vals: " + map.values());
//
////            HashMap<String, ssfEvent.EventType> EventUri = new HashMap<>();
////
////            EventUri.put("https://schemas.openid.net/secevent/caep/event-type/session-revoked", ssfEvent.EventType.SESSIONREVOKED);
////            EventUri.put("https://schemas.openid.net/secevent/caep/event-type/credential-change", ssfEvent.EventType.CREDENTIALCHANGE);
////            EventUri.put("https://schemas.openid.net/secevent/caep/event-type/device-compliance-change", ssfEvent.EventType.DEVICECOMPLIANCE);
////            EventUri.put("https://schemas.openid.net/secevent/caep/event-type/assurance-level-change", ssfEvent.EventType.ASSURANCELEVELCHANGE);
////            EventUri.put("https://schemas.openid.net/secevent/caep/event-type/token-claims-change", ssfEvent.EventType.TOKENCLAIMSCHANGE);
////            EventUri.put("https://schemas.openid.net/secevent/ssf/event-type/verification", ssfEvent.EventType.VERIFICATIONEVENTTYPE);
////            EventUri.put("https://schemas.openid.net/secevent/caep/event-type/stream-updated", ssfEvent.EventType.STREAMUPDATEDEVENTTYPE);
//
//
////
////            Object[] claimMapKeys = claim_map.keySet().toArray();
////            String uri = claimMapKeys[0].toString();
////
////            Object[] claimMapValues = claim_map.values().toArray();
////            String values = claimMapValues[0].toString();
////
////            System.out.println();
////            System.out.println("This is a(n) " + EventUri.get(uri) + " event. This is the event value: " + values);
//        }



//        ArrayList<SsfEvent> eventSet = new ArrayList<>();
//        //SsfEvent ssf1 = new SessionRevokedEvent();
//        //SsfEvent ssf2 = new CredentialChangeEvent();
//        //SsfEvent ssf3 = new StreamUpdatedEvent();
//        //eventSet.add(ssf1);
//       // eventSet.add(ssf2);
//       // eventSet.add(ssf3);
//
//        for (SsfEvent event: eventSet) {
//            event.printEvent();
//        }





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
