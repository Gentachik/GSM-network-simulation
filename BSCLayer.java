import javax.swing.*;
import java.awt.*;

public class BSCLayer extends JPanel {
    private BSCInterface logic;
    private static int idCounter=1;
    private boolean go=true;
    private int id;
    private int messagesCount;
    BSCLayer(){
        super();
        id=idCounter;
        idCounter++;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        workProcess();
    }
    private void updateLayer(int stationCounter){
        int componentCount=this.getComponentCount();
        for (int i = componentCount; i < stationCounter; i++) {
            BSCstation station = new BSCstation();
            station.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            this.add(station);
            if(logic!=null){
                logic.getBSCstation(station);
            }
        }
        this.revalidate();
        this.repaint();
    }
    private void workProcess(){
        new Thread(() -> {
            while (go) {
                try {
                    if(logic!=null) {
                        messagesCount = logic.getMessageCount();
                    }
                    int stationCounter = (messagesCount / 5 + 1);
                    updateLayer(stationCounter);
                    this.repaint();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void terminate(){go=false;}
    public int getId(){return id;}
    public void setLogic(BSCInterface inter){logic=inter;}
}