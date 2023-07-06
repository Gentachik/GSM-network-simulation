public class VRDLogic {
    private int id;
    private Thread runStation;
    private Thread refreshThread;
    private MessageCounter station;
    private int messageCount = 0;
    private boolean go = true;
    private boolean refresh = true;
    public VRDLogic(VRDstation station) {
        id = station.getId();
        setTelephoneNumber(id);
        run();
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
    public void setStation(MessageCounter station) {this.station = station;}
    private void run() {
        runStation = new Thread(new Runnable() {
            public void run() {
                while (go) {
                    if (station != null) {
                        station.getMessageCounter(messageCount);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        runStation.start();
    }
    public String getTelephoneNumber(){return telephoneNumber;}
    private void refresh() {
        refreshThread = new Thread(new Runnable() {
            public void run() {
                while (go) {
                    if (refresh) {
                        messageCount = 0;
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
    public void increaseMessageCount() {messageCount += 1;}
    public void setRefresh(boolean status) {refresh = status;}
}
