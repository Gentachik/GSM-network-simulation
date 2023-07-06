import java.util.ArrayList;

public class BSCLayerLogic implements BSCInterface {
    private BSCLayerLogic nextLayer=null;
    private Inter logic;
    private final int id;
    private final ArrayList<BSCLogic> bscList;
    public BSCLayerLogic(BSCLayer layer) {
        bscList=new ArrayList<>();
        id=layer.getId();
    }
    public int getId() {return id;}
    @Override
    public void getBSCstation(BSCstation station) {
        BSCLogic bscLogic=new BSCLogic();
        bscList.add(bscLogic);
        bscLogic.setLayer(this);
        bscLogic.setLogic(logic);
    }
    @Override
    public int getMessageCount(){
        int messageCount=0;
        for(int i=0;i<bscList.size();i++){
            messageCount+=bscList.get(i).getMessageCount();
        }
        return messageCount;
    }
    public ArrayList<BSCLogic> getBscList(){return bscList;}
    public void takeMessage(String message) {
        int minMessage = 5;
        int index = 0;
        for (int i = 0; i < bscList.size(); i++) {
            BSCLogic logic = bscList.get(i);
            if (logic.getMessageCount() < minMessage) {
                index = i;
                minMessage = getMessageCount();
            }
        }
        bscList.get(index).takeMessage(message);
    }
    public BSCLayerLogic getNextLayer(){return nextLayer;}
    public void setNextLayer(BSCLayerLogic nextLayer){this.nextLayer=nextLayer;}
    public void setLogic(Inter inter){logic=inter;}
}