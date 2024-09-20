import java.util.HashMap;
import java.util.Map;

public class VerificationEvent implements SsfEvent{
    public Map<String, Object> json; //is this correct?????
    String state;

    public VerificationEvent(Map<String, Object> json, String state){
        this.json = json;
        this.state = state;
    }

    @Override
    public String getEventUri(){
        return "https://schemas.openid.net/secevent/ssf/event-type/verification";
    }

    @Override
    public SsfEventClass.SubjectFormat getSubjectFormat(){
        return SsfEventClass.SubjectFormat.UNKNOWN;
    }

    @Override
    public Map<String, Object> getSubject(){
        return new HashMap<>();
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public SsfEventClass.EventType getType(){
        return SsfEventClass.EventType.VERIFICATIONEVENTTYPE;
    }

    public String getState(){
        return state;
    }

    @Override
    public void printEvent(){
        System.out.println("This is a(n) " + getType() + " event.");
    }
}
