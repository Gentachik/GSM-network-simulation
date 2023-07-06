import java.util.ArrayList;

public interface Inter {
    void getVBD(VBDstation station);
    void getVRD(VRDstation station);
    int getMessageCountLeft();
    int getMessageCountRight();
    void getBTSLeft(BTSstation station) throws StationNotFoundException;
    void getBTSRight(BTSstation station) throws StationNotFoundException;
    void deleteBTS(int id);
    void getBSCLayer(BSCLayer layer);
    void deleteBSCLayer(BSCLayer station);
    ArrayList<BTSLogic> getBTSLeftList();
    ArrayList<BTSLogic> getBTSRightList();
    ArrayList<BSCLayerLogic> getBSCLayerList();
    ArrayList<VRDLogic> getVRDList();
}