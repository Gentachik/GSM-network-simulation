import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BSCLogic {
    private Inter inter;
    private BSCLayerLogic layer;
    private boolean go=true;
    private int id;
    private ArrayList<String> messageList=new ArrayList<>();
    public BSCLogic(BSCstation station) {
        id=station.getId();
        run();
    }
    private void run(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (go) {
                    if(!messageList.isEmpty()){
                        if(layer.getNextLayer()==null){
                            int index=0;
                            int minMessage=5;
                            ArrayList<BTSLogic> btsLogics=inter.getBTSRightList();
                            for(int i=0;i< btsLogics.size();i++){
                                if(btsLogics.get(i).getMessageCount()<minMessage){
                                    index=i;
                                    minMessage=btsLogics.get(i).getMessageCount();
                                }
                            }
                            btsLogics.get(index).takeMessage(messageList.get(0));
                            messageList.remove(0);
                            Collections.rotate(messageList, -1);
                        }else {
                            int index=0;
                            int minMessage=5;
                            ArrayList<BSCLogic> bscLogics=layer.getNextLayer().getBscList();
                            for(int i=0;i< bscLogics.size();i++){
                                if(bscLogics.get(i).getMessageCount()<minMessage){
                                    index=i;
                                    minMessage=bscLogics.get(i).getMessageCount();
                                }
                            }
                            bscLogics.get(index).takeMessage(messageList.get(0));
                            messageList.remove(0);
                            Collections.rotate(messageList, -1);
                        }
                    }
                    try {
                        Random random = new Random();
                        int randomNumber = random.nextInt(11) + 5;
                        Thread.sleep(randomNumber*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
    public int getMessageCount(){return messageList.size();}
    public void terminate(){
        go=false;
        while (!messageList.isEmpty()){
            if(layer.getNextLayer()==null){
                int index=0;
                int minMessage=5;
                ArrayList<BTSLogic> btsLogics=inter.getBTSRightList();
                for(int i=0;i< btsLogics.size();i++){
                    if(btsLogics.get(i).getMessageCount()<minMessage){
                        index=i;
                        minMessage=btsLogics.get(i).getMessageCount();
                    }
                }
                btsLogics.get(index).takeMessage(messageList.get(0));
                messageList.remove(0);
                Collections.rotate(messageList, -1);
            }else {
                int index=0;
                int minMessage=5;
                ArrayList<BSCLogic> bscLogics=layer.getNextLayer().getBscList();
                for(int i=0;i< bscLogics.size();i++){
                    if(bscLogics.get(i).getMessageCount()<minMessage){
                        index=i;
                        minMessage=bscLogics.get(i).getMessageCount();
                    }
                }
                bscLogics.get(index).takeMessage(messageList.get(0));
                messageList.remove(0);
                Collections.rotate(messageList, -1);
            }
        }
    }
    public void takeMessage(String message) {messageList.add(message);}
    public void setLayer(BSCLayerLogic bscLayerLogic) {layer=bscLayerLogic;}
    public void setLogic(Inter inter){this.inter=inter;}
}