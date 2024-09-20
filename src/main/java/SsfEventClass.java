import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.ExceptionListener;
import java.util.*;
import java.util.stream.Stream;

//FIX FORMATTING (CAPITALIZED WORDS SHOULD HAVE UNDERSCORES IN BETWEEN)
//how necessary is this because it might just break everything
interface SsfEvent {
    String getEventUri();
    SsfEventClass.SubjectFormat getSubjectFormat();
    Map<String, Object> getSubject();
    long getTimestamp();
    SsfEventClass.EventType getType();
    void printEvent();
}


public class SsfEventClass {
    //enum and uri are supposed to go here?
    //enum for subject format?

    public enum EventType {
        SESSIONREVOKED,
        CREDENTIALCHANGE,
        DEVICECOMPLIANCE,
        ASSURANCELEVELCHANGE,
        TOKENCLAIMSCHANGE,
        VERIFICATIONEVENTTYPE,
        STREAMUPDATEDEVENTTYPE
    }

    public enum SubjectFormat{
        ACCOUNT,
        EMAIL,
        ISSUERANDSUBJECT,
        OPAQUE,
        PHONENUMBER,
        DECENTRALIZEDIDENTIFIER,
        UNIQUERESOURCEIDENTIFIER,
        ALIASES,
        COMPLEXSUBJECT,
        UNKNOWN                    //special case

    }

    public static final String ACCOUNT_SUBJECT_FORMAT = "account";
    public static final String EMAIL_SUBJECT_FORMAT = "email";
    public static final String ISSUER_AND_SUBJECT_FORMAT = "iss_sub";
    public static final String OPAQUE_SUBJECT_FORMAT = "opaque";
    public static final String PHONE_NUMBER_SUBJECT_FORMAT = "phone_number";
    public static final String DECENTRALIZED_IDENTIFIER_SUBJECT_FORMAT = "did";
    public static final String UNIQUE_RESOURCE_IDENTIFIER_SUBJECT_FORMAT = "uri";
    public static final String ALIASES_SUBJECT_FORMAT = "aliases";

    static HashMap<String, EventType> EventUri = new HashMap<>( );

    public static HashMap<String, EventType> getEventUri() {
        setEventUri(EventUri);
        return EventUri;
    }

    public static void setEventUri(HashMap<String, EventType> EventUri){
        EventUri.put("https://schemas.openid.net/secevent/caep/event-type/session-revoked", SsfEventClass.EventType.SESSIONREVOKED);
        EventUri.put("https://schemas.openid.net/secevent/caep/event-type/credential-change", SsfEventClass.EventType.CREDENTIALCHANGE);
        EventUri.put("https://schemas.openid.net/secevent/caep/event-type/device-compliance-change", SsfEventClass.EventType.DEVICECOMPLIANCE);
        EventUri.put("https://schemas.openid.net/secevent/caep/event-type/assurance-level-change", SsfEventClass.EventType.ASSURANCELEVELCHANGE);
        EventUri.put("https://schemas.openid.net/secevent/caep/event-type/token-claims-change", SsfEventClass.EventType.TOKENCLAIMSCHANGE);
        EventUri.put("https://schemas.openid.net/secevent/ssf/event-type/verification", SsfEventClass.EventType.VERIFICATIONEVENTTYPE);
        EventUri.put("https://schemas.openid.net/secevent/caep/event-type/stream-updated", SsfEventClass.EventType.STREAMUPDATEDEVENTTYPE);
    }

    public static Map<String, Object> extractSubject(Map<String, Object> claimsJson, Map<String, Object> subjectAttributes) throws Exception {
        Object subId = claimsJson.get("sub_id");
        if (subId != null){
            if (subId instanceof Map){
                return (Map<String, Object>) subId;
            } else {
                throw new Exception("cannot retrieve subject of an event");
            }
        }

        Object subject = subjectAttributes.get("subject");
        if(subject != null){
            if (subject instanceof Map) {
                return (Map<String, Object>) subject;
            } else {
                throw new Exception("cannot retrieve subject of an event");
            }
        }

        return null;
    }


    //general question: should i be casting to linkedHashmap or should i just do map
    public static SsfEvent eventStructFromStruct(String eventUri, Object eventSubject, Map<String, Object> claimsJson) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SsfEvent event;

        /*
        write a switch case statement to create an empty object of type whatever event type
         */

        EventType eventEnum = getEventUri().get(eventUri);
        System.out.println(eventEnum);
        System.out.println(eventSubject);

        Collection<Map<String, Object>> subjectAttributesList;
        LinkedHashMap<String, Object> subjectAttributes;


        if (eventSubject instanceof Collection){
            subjectAttributesList = (Collection<Map<String, Object>>) eventSubject;
        } else {
            throw new Exception("Unable to parse event subject");
        }

        Object[] subjAttributesTemp = subjectAttributesList.toArray(); //saves as a LinkedHashMap
        if (subjAttributesTemp[0] instanceof LinkedHashMap){
            subjectAttributes = (LinkedHashMap<String, Object>) subjAttributesTemp[0];
        } else {
            throw new Exception("Unable to parse event subject");
        }


        //special event types
        if (eventEnum == EventType.VERIFICATIONEVENTTYPE) {
            String state = (String) subjectAttributes.get("state");
            if (state == null) {
                throw new Exception("unable to parse state");
            }
            event = new VerificationEvent(claimsJson, state);
            return event;

        } else if (eventEnum == EventType.STREAMUPDATEDEVENTTYPE){
            String status = (String) subjectAttributes.get("status");
            if (status == null) {
                throw new Exception("unable to parse status");
            }

            String reason = (String) subjectAttributes.get("reason");

            event = new StreamUpdatedEvent(claimsJson, status, reason);
            return event;
        }

        long timestamp;
        try {
            timestamp = (int) subjectAttributes.get("event_timestamp");
        } catch (Exception e) {
            throw new Exception("Unable to parse event timestamp");
        }

        Map<String, Object> subject = extractSubject(claimsJson, subjectAttributes);
        if (subject == null) {
            return null;
        }

        SubjectFormat format = getSubjectFormat(subject);
        if (format == SubjectFormat.UNKNOWN) {
            return null;
        }

        switch (eventEnum) {
            case CREDENTIALCHANGE:
                String rawCredentialType = (String) subjectAttributes.get("credential_type");
                if (rawCredentialType == null) {
                    throw new Exception("unable to parse credential type of a credential change event");
                }

                CredentialChangeEvent.CredentialType credentialType = CredentialChangeEvent.credentialTypesMap.get(rawCredentialType);
                if (credentialType == null) {
                    throw new Exception("received invalid credential type for a credential change event");
                }

                String rawChangeType = (String) subjectAttributes.get("change_type");
                if (rawChangeType == null) {
                    throw new Exception("unable to parse change type of a credential change event");
                }

                CredentialChangeEvent.ChangeType changeType = CredentialChangeEvent.changeTypesMap.get(rawChangeType);
                if (changeType == null) {
                    throw new Exception("received invalid change type for a credential change event");
                }
                event = new CredentialChangeEvent(claimsJson, format, subject, timestamp, credentialType, changeType);
                return event;

            case SESSIONREVOKED:
                event = new SessionRevokedEvent(claimsJson, format, subject, timestamp);
                return event;

            case DEVICECOMPLIANCE:
                String previousStatus = (String) subjectAttributes.get("previousStatus");
                if (previousStatus == null) {
                    throw new Exception("unable to parse previous status");
                }

                String currentStatus = (String) subjectAttributes.get("currentStatus");
                if (currentStatus == null) {
                    throw new Exception("unable to parse current status");
                }
                event = new DeviceComplianceEvent(claimsJson, format, subject, timestamp, previousStatus, currentStatus);
                return event;

            case ASSURANCELEVELCHANGE:
                String previousLevel = (String) subjectAttributes.get("previousLevel");
                String changeDirection = (String) subjectAttributes.get("changeDirection");

                String currentLevel = (String) subjectAttributes.get("currentLevel");
                if (currentLevel == null) {
                    throw new Exception("unable to parse current level");
                }

                String namespace = (String) subjectAttributes.get("namespace");
                if (namespace == null) {
                    throw new Exception("unable to parse namespace");
                }
                //are previous and change direction correct?
                event = new AssuranceLevelChangeEvent(claimsJson, format, subject, timestamp, namespace, previousLevel, currentLevel, changeDirection);
                return event;

            case TOKENCLAIMSCHANGE:
                Map<String, Object> claims;
                if (subjectAttributes.get("claims") instanceof Map) {
                    claims = (Map<String, Object>) subjectAttributes.get("claims");
                } else {
                    throw new Exception("unable to parse claims");
                }
                event = new TokenClaimsChangeEvent(claimsJson, format, subject, timestamp, claims);
                return event;

            default:
                System.err.println("No matching events"); //hope this works! OR do throw new Exception("No matching events"); ?
                return null;
        }
    }

    public static SubjectFormat getSubjectFormat(Map<String, Object> subject) throws Exception{
        Object format = subject.get("format");
        if (format == null) {
            return SubjectFormat.COMPLEXSUBJECT;
        }

        String formatString = "";
        if (format instanceof String) {
            formatString = String.format("%s", format);
        } else {
            throw new Exception("unable to determine subject format");
        }

        switch (formatString) {
            case ACCOUNT_SUBJECT_FORMAT:
                return SubjectFormat.ACCOUNT;
            case EMAIL_SUBJECT_FORMAT:
                return SubjectFormat.EMAIL;
            case ISSUER_AND_SUBJECT_FORMAT:
                return SubjectFormat.ISSUERANDSUBJECT;
            case OPAQUE_SUBJECT_FORMAT:
                return SubjectFormat.OPAQUE;
            case PHONE_NUMBER_SUBJECT_FORMAT:
                return SubjectFormat.PHONENUMBER;
            case DECENTRALIZED_IDENTIFIER_SUBJECT_FORMAT:
                return SubjectFormat.DECENTRALIZEDIDENTIFIER;
            case UNIQUE_RESOURCE_IDENTIFIER_SUBJECT_FORMAT:
                return SubjectFormat.UNIQUERESOURCEIDENTIFIER;
            case ALIASES_SUBJECT_FORMAT:
                return SubjectFormat.ALIASES;
            default:
                return SubjectFormat.UNKNOWN;
        }
    }

    public String[] eventTypeArrayToEventUriArray(EventType[] events){
        return null;
    }



}
