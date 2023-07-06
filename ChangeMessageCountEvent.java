import java.util.EventObject;

public class ChangeMessageCountEvent extends EventObject {
    private int messageCount;
    public ChangeMessageCountEvent(Object source, int messageCount) {
        super(source);
        this.messageCount=messageCount;
    }
    public int getMessageCount(){return messageCount;}
}
