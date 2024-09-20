import java.util.HashMap;
import java.util.Map;

public class TokenClaimsChangeEvent implements SsfEvent{
    public Map<String, Object> json; //is this correct?????
    public SsfEventClass.SubjectFormat format; //a bit confused on whether this should be SubjectFormat format or the other way
    public Map<String, Object> subject;
    public long eventTimestamp;
    public Map<String, Object> claims;

    public TokenClaimsChangeEvent(Map<String, Object> json, SsfEventClass.SubjectFormat format, Map<String, Object> subject, long eventTimestamp, Map<String, Object> claims){
        this.json = json;
        this.format = format;
        this.subject = subject;
        this.eventTimestamp = eventTimestamp;
        this.claims = claims;
    }

    @Override
    public String getEventUri(){
        return "https://schemas.openid.net/secevent/caep/event-type/token-claims-change";
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

    @Override
    public SsfEventClass.EventType getType(){
        return SsfEventClass.EventType.TOKENCLAIMSCHANGE;
    }

    public Map<String, Object> getClaims(){
        return claims;
    }

    @Override
    public void printEvent(){
        System.out.println("This is a(n) " + getType() + " event.");
    }
}
