import java.util.HashMap;
import java.util.Map;

public class CredentialChangeEvent implements SsfEvent{
    public enum CredentialType {
        PASSWORD("password"),
        PIN("pin"),
        X509("x509"),
        FIDO2_PLATFORM("fido2_platform"),
        FIDO2_ROAMING("fido2_roaming"),
        FIDO_U2F("fido_u2f"),
        VERIFIABLE_CREDENTIAL("verifiable_credential"),
        PHONE_VOICE("phone_voice"),
        PHONE_SMS("phone_sms"),
        APP("app");

        private final String value;

        CredentialType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public enum ChangeType {
        CREATE("create"),
        REVOKED("revoked"),
        UPDATE("update"),
        DELETE("delete");

        private final String value;

        ChangeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    static HashMap<String, CredentialType> credentialTypesMap = new HashMap<>();
    static {
        credentialTypesMap.put("password", CredentialType.PASSWORD);
        credentialTypesMap.put("pin", CredentialType.PIN);
        credentialTypesMap.put("x509", CredentialType.X509);
        credentialTypesMap.put("fido2-platform", CredentialType.FIDO2_PLATFORM);
        credentialTypesMap.put("fido2-roaming", CredentialType.FIDO2_ROAMING);
        credentialTypesMap.put("fido-u2f", CredentialType.FIDO_U2F);
        credentialTypesMap.put("verifiable-credential", CredentialType.VERIFIABLE_CREDENTIAL);
        credentialTypesMap.put("phone-voice", CredentialType.PHONE_VOICE);
        credentialTypesMap.put("phone-sms", CredentialType.PHONE_SMS);
        credentialTypesMap.put("app", CredentialType.APP);
    }

    static HashMap<String, ChangeType> changeTypesMap = new HashMap<>();
    static {
        changeTypesMap.put("create", ChangeType.CREATE);
        changeTypesMap.put("revoke", ChangeType.REVOKED);
        changeTypesMap.put("update", ChangeType.UPDATE);
        changeTypesMap.put("delete", ChangeType.DELETE);
    }

    public Map<String, Object> json; //is this correct?????
    public SsfEventClass.SubjectFormat format; //a bit confused on whether this should be SubjectFormat format or the other way
    public Map<String, Object> subject;
    public long eventTimestamp;
    CredentialType credentialType;
    ChangeType changeType;

    public CredentialChangeEvent (Map<String, Object> json, SsfEventClass.SubjectFormat format, Map<String, Object> subject, long eventTimestamp, CredentialType credentialType, ChangeType changeType) {
        this.json = json;
        this.format = format;
        this.subject = subject;
        this.eventTimestamp = eventTimestamp;
        this.credentialType = credentialType;
        this.changeType = changeType;
    }

    @Override
    public String getEventUri(){
        return "https://schemas.openid.net/secevent/caep/event-type/credential-change";
    }

    @Override
    public SsfEventClass.SubjectFormat getSubjectFormat(){
        return format;
    }

    @Override
    public Map<String, Object> getSubject(){
        return subject;
    }

    @Override
    public long getTimestamp() {
        return eventTimestamp;
    }

    public CredentialType getCredentialType(){
        return credentialType; //should this be getValue() and return a String?
    }

    public ChangeType getChangeType(){
        return changeType; //should this be getValue() and return a String?
    }

    @Override
    public SsfEventClass.EventType getType(){
        return SsfEventClass.EventType.CREDENTIALCHANGE;
    }

    @Override
    public void printEvent(){
        System.out.println("This is a(n) " + getType() + " event.");
    }



}
