public class Message {
    private String from;
    private String to;
    private String wholeMessage;

    public Message() {
    }

    public Message(String from, String to, String wholeMessage) {
        this.from = from;
        this.to = to;
        this.wholeMessage = wholeMessage;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getWholeMessage() {
        return wholeMessage;
    }

    public void setWholeMessage(String wholeMessage) {
        this.wholeMessage = wholeMessage;
    }

    @Override
    public String toString() {
        return wholeMessage;
    }
}
