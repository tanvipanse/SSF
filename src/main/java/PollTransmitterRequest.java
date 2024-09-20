import java.util.List;

public class PollTransmitterRequest {
    private List<String> acknowledgements;
    private int maxEvents;
    private boolean returnImmediately;

    public PollTransmitterRequest(List<String> acknowledgements, int maxEvents, boolean returnImmediately){
        this.acknowledgements = acknowledgements;
        this.maxEvents = maxEvents;
        this.returnImmediately = returnImmediately;
    }

    public List<String> getAcknowledgements() {
        return acknowledgements;
    }

    public void setAcknowledgements(List<String> acknowledgements) {
        this.acknowledgements = acknowledgements;
    }

    public int getMaxEvents() {
        return maxEvents;
    }

    public void setMaxEvents(int maxEvents) {
        this.maxEvents = maxEvents;
    }

    public boolean isReturnImmediately() {
        return returnImmediately;
    }

    public void setReturnImmediately(boolean returnImmediately) {
        this.returnImmediately = returnImmediately;
    }
}
