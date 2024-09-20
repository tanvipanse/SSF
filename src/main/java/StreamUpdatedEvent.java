import java.util.HashMap;
import java.util.Map;

public class StreamUpdatedEvent implements SsfEvent {
    public Map<String, Object> json; //is this correct?????
    String status;
    String reason;

    public StreamUpdatedEvent(Map<String, Object> json, String status, String reason){
        this.json = json;
        this.status = status;
        this.reason = reason;
    }
    @Override
    public String getEventUri(){
        return "https://schemas.openid.net/secevent/caep/event-type/stream-updated";
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
        return SsfEventClass.EventType.STREAMUPDATEDEVENTTYPE;
    }

    public String getState(){
        return status;
    }

    @Override
    public void printEvent(){
        System.out.println("This is a(n) " + getType() + " event.");
    }
}
