import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Logic
        implements
        Inter,
        VBDdelete,
        VRDdelete{
    private ArrayList<VBDLogic> vbdList;
    private ArrayList<VRDLogic> vrdList;
    private ArrayList<BTSLogic> btsLeftList;
    private ArrayList<BTSLogic> btsRightList;
    private ArrayList<BSCLayerLogic> bscLayerLogics;
    public void run(){
        vbdList=new ArrayList<>();
        btsLeftList=new ArrayList<>();
        bscLayerLogics=new ArrayList<>();
        btsRightList=new ArrayList<>();
        vrdList=new ArrayList<>();
    }
    @Override
    public void getVBD(VBDstation station) {
        VBDLogic logic=new VBDLogic(station);
        logic.setLogic(this);
        vbdList.add(logic);
        station.addLogic(this);
        station.setVBDLogic(logic);
    }
    @Override
    public void getVRD(VRDstation station) {
        VRDLogic logic=new VRDLogic(station);
        vrdList.add(logic);
        station.addLogic(this);
        station.setLogic(this);
        logic.setStation(station);
    }
    @Override
    public int getMessageCountLeft() {
        int messageCount=0;
        for(int i=0;i<btsLeftList.size();i++){
            BTSLogic logic=btsLeftList.get(i);
            messageCount+=logic.getMessageCount();
        }
        return messageCount;
    }
    @Override
    public int getMessageCountRight() {
        int messageCount=0;
        for(int i=0;i<btsRightList.size();i++){
            BTSLogic logic=btsRightList.get(i);
            messageCount+=logic.getMessageCount();
        }
        return messageCount;
    }
    @Override
    public void getBTSLeft(BTSstation station) throws StationNotFoundException {
        BTSLogic logic=new BTSLogic(station);
        logic.setLogic(this);
        btsLeftList.add(logic);
    }
    @Override
    public void getBTSRight(BTSstation station) throws StationNotFoundException {
        BTSLogic logic=new BTSLogic(station);
        logic.setLogic(this);
        logic.setRight();
        btsRightList.add(logic);
    }
    @Override
    public void deleteBTS(int id) {
        for(BTSLogic station : btsLeftList){
            if(station.getId()==id){
                station.terminate();
                btsLeftList.remove(station);
                break;
            }
        }
    }
    @Override
    public void getBSCLayer(BSCLayer layer) {
        BSCLayerLogic logic=new BSCLayerLogic(layer);
        logic.setLogic(this);
        if(!bscLayerLogics.isEmpty()) {
            bscLayerLogics.get(bscLayerLogics.size() - 1).setNextLayer(logic);
        }
        bscLayerLogics.add(logic);
        layer.setLogic(logic);
    }
    @Override
    public void deleteBSCLayer(BSCLayer station) {
        for (BSCLayerLogic layer : bscLayerLogics){
            if(layer.getId()==station.getId()){
                for(int i=0;i<layer.getBscList().size();i++){
                    layer.getBscList().get(i).terminate();
                }
                layer.terminate();
                station.terminate();
                bscLayerLogics.get(bscLayerLogics.size()-1).setNextLayer(null);
                break;
            }
        }
    }
    @Override
    public ArrayList<BTSLogic> getBTSLeftList() {
        return btsLeftList;
    }
    @Override
    public ArrayList<BTSLogic> getBTSRightList() {return btsRightList;}
    @Override
    public ArrayList<BSCLayerLogic> getBSCLayerList() {
        return bscLayerLogics;
    }
    @Override
    public ArrayList<VRDLogic> getVRDList() {return vrdList;}
    @Override
    public void save() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("project3.bin");
        OutputStreamWriter writer = new OutputStreamWriter(fileOut, StandardCharsets.UTF_8);
        for (int i=0;i< vbdList.size();i++){
            writer.write("id: "+String.valueOf(vbdList.get(i).getId()) + " ");
            writer.write(",message: "+vbdList.get(i).getMessage() + " ");
            writer.write(",count of messages: "+String.valueOf(vbdList.get(i).getMessageSent()) + "\n");
        }
        writer.close();
        fileOut.close();
    }
    @Override
    public void deleteIdVBD(int id) {
        for(VBDLogic station : vbdList){
            if(station.getId()==id){
                station.terminateStation();
                vbdList.remove(station);
                break;
            }
        }
    }
    @Override
    public void deleteIdVRD(int id) {
        for(VRDLogic station : vrdList){
            if(station.getId()==id){
                station.terminateStation();
                vrdList.remove(station);
                break;
            }
        }
    }
}
