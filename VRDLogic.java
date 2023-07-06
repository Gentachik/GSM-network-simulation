public class VRDLogic {
    private final int id;
    private MessageCountListener station;
    private int messageCount = 0;
    private boolean go = true;
    private boolean refresh = true;
    public VRDLogic(VRDstation station) {
        id = station.getId();
        setTelephoneNumber(id);
        refresh();
    }
    private String telephoneNumber;
    private void setTelephoneNumber(int i) {
        telephoneNumber = "+" +i;
        if(Integer.toString(i).length()<11){
            for (int j = 0; j < 11-Integer.toString(i).length(); j++) {
                telephoneNumber += "0";
            }
        }
    }
    public int getId() {return id;}
    public void setStation(MessageCountListener station) {this.station = station;}
    public String getTelephoneNumber(){return telephoneNumber;}
    private void refresh() {
        Thread refreshThread = new Thread(new Runnable() {
            public void run() {
                while (go) {
                    if (refresh) {
                        messageCount = 0;
                        station.setMessageCount(new ChangeMessageCountEvent(this, messageCount));
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        refreshThread.start();
    }
    public void terminateStation() {go = false;}
    public void increaseMessageCount() {
        messageCount+=1;
        station.setMessageCount(new ChangeMessageCountEvent(this, messageCount));}
    public void setRefresh(boolean status) {refresh = status;}
}
