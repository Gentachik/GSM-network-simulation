import javax.swing.*;
import java.awt.*;

public  class BTSLayer extends JPanel {
    private Inter logic;
    private boolean isLeft=true;
    private int messagesCount;
    BTSLayer() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        workProcess();
    }
    private void updateLayer(int stationCounter) throws StationNotFoundException {
        int componentCount=this.getComponentCount();
        for (int i = componentCount; i < stationCounter; i++) {
            BTSstation station = new BTSstation();
            station.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            this.add(station);
            if(logic!=null){
                station.setLogic(logic);
                if(isLeft) {
                    logic.getBTSLeft(station);
                }else {
                    logic.getBTSRight(station);
                }
            }
        }

        this.revalidate();
        this.repaint();
    }
    private void workProcess() {
        new Thread(() -> {
            while (true) {
                try {
                    if(logic!=null) {
                        if(isLeft) {
                            messagesCount = logic.getMessageCountLeft();
                        }else {
                            messagesCount = logic.getMessageCountRight();
                        }
                        int stationCounter = (messagesCount / 5 + 1);
                        updateLayer(stationCounter);
                    }
                    this.repaint();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (StationNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void setRight(){isLeft=false;}
    public boolean getSide(){return isLeft;}
    public void setLogic(Inter inter){logic=inter;}
}
