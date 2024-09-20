import java.util.HashMap;
import java.util.Map;

public class DeviceComplianceEvent implements SsfEvent{
    public Map<String, Object> json; //is this correct?????
    public SsfEventClass.SubjectFormat format; //a bit confused on whether this should be SubjectFormat format or the other way
    public Map<String, Object> subject;
    public long eventTimestamp;
    String previousStatus;
    String currentStatus;

    public DeviceComplianceEvent(Map<String, Object> json, SsfEventClass.SubjectFormat format, Map<String, Object> subject, long eventTimestamp, String previousStatus, String currentStatus){
        this.json = json;
        this.format = format;
        this.subject = subject;
        this.eventTimestamp = eventTimestamp;
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
    }

    @Override
    public String getEventUri(){
        return "https://schemas.openid.net/secevent/caep/event-type/device-compliance-change";
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

    public String getPreviousStatus() {
        return previousStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    @Override
    public SsfEventClass.EventType getType(){
        return SsfEventClass.EventType.DEVICECOMPLIANCE;
    }

    @Override
    public void printEvent(){
        System.out.println("This is a(n) " + getType() + " event.");
    }

}

