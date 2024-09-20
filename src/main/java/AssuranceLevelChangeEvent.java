import java.util.HashMap;
import java.util.Map;

public class AssuranceLevelChangeEvent implements SsfEvent{
    public Map<String, Object> json; //is this correct?????
    public SsfEventClass.SubjectFormat format; //a bit confused on whether this should be SubjectFormat format or the other way
    public Map<String, Object> subject;
    public long eventTimestamp;
    String namespace;
    String previousLevel;
    String currentLevel;
    String changeDirection;

    public AssuranceLevelChangeEvent(Map<String, Object> json, SsfEventClass.SubjectFormat format, Map<String, Object> subject, long eventTimestamp, String namespace, String previousLevel, String currentLevel, String changeDirection){
        this.json = json;
        this.format = format;
        this.subject = subject;
        this.eventTimestamp = eventTimestamp;
        this.namespace = namespace;
        this.previousLevel = previousLevel;
        this.currentLevel = currentLevel;
        this.changeDirection = changeDirection;
    }

    @Override
    public String getEventUri() {
        return "https://schemas.openid.net/secevent/caep/event-type/assurance-level-change";
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
        return previousLevel;
    }

    public String getCurrentStatus() {
        return currentLevel;
    }

    public String getChangeDirection() {
        return changeDirection;
    }

    public String getNamespace(){
        return namespace;
    }

    @Override
    public SsfEventClass.EventType getType(){
        return SsfEventClass.EventType.ASSURANCELEVELCHANGE;
    }

    @Override
    public void printEvent(){
        System.out.println("This is a(n) " + getType() + " event.");
    }
}
