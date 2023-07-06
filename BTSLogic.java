import java.util.ArrayList;
import java.util.Collections;

public  class BTSLogic {
    private ArrayList<String> messageList=new ArrayList<>();
    private boolean isLeft=true;
    private boolean go=true;
    private final int id;
    private Inter logic;
    public BTSLogic(BTSstation station) {
        id=station.getID();
        run();
    }
    public int getMessageCount() {return messageList.size();}
    public int getId() {return id;}
    public void takeMessage(String message) {messageList.add(message);}
    private void run() {
        Thread thread = new Thread(() -> {
            while (go) {
                if(logic!=null) {
                    if(isLeft) {
                        if (!messageList.isEmpty()) {
                            BSCLayerLogic bscLayer = logic.getBSCLayerList().get(0);
                            bscLayer.takeMessage(messageList.get(0));
                            messageList.remove(0);
                            Collections.rotate(messageList, -1);
                        }
                    }else {
                        if (!messageList.isEmpty()) {
                            ArrayList<VRDLogic> vrdLogics=logic.getVRDList();
                            if(!vrdLogics.isEmpty()) {
                                boolean exist=false;
                                for (int i=0;i<vrdLogics.size();i++){
                                    String receiverTelephone= decodeMessage(messageList.get(0));
                                    VRDLogic vrdLogic=vrdLogics.get(i);
                                    if(receiverTelephone.equals(vrdLogic.getTelephoneNumber())){
                                        vrdLogic.increaseMessageCount();
                                        messageList.remove(0);
                                        Collections.rotate(messageList, -1);
                                        exist=true;
                                        break;
                                    }
                                }
                                if(!exist){
                                    messageList.remove(0);
                                    Collections.rotate(messageList, -1);
                                    try {
                                        throw new StationNotFoundException();
                                    } catch (StationNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }else {
                                messageList.remove(0);
                                Collections.rotate(messageList, -1);
                                try {
                                    throw new StationNotFoundException();
                                } catch (StationNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    private static String decodeMessage(String encodeMessage){
        String result;
        result=encodeMessage.substring(0, 36);
        result=result.substring(20);
        result=decodeNumber(result);
        return result;
    }
    private static String decodeNumber(String encodedNumber) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < encodedNumber.length()) {
            result.append(encodedNumber.charAt(i + 1)).append(encodedNumber.charAt(i));
            i += 2;
        }
        if (result.toString().endsWith("F")) {
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        result = new StringBuilder(result.substring(4));
        result.insert(0, "+");
        return result.toString();
    }
    public void terminate(){
        go=false;
        while (!messageList.isEmpty()){
            if(isLeft) {
                BSCLayerLogic bscLayer = logic.getBSCLayerList().get(0);
                bscLayer.takeMessage(messageList.get(0));
                messageList.remove(0);
                Collections.rotate(messageList, -1);
            }else {
                ArrayList<VRDLogic> vrdLogics=logic.getVRDList();
                if(!vrdLogics.isEmpty()) {
                    boolean exist=false;
                    for (int i=0;i<vrdLogics.size();i++){
                        String receiverTelephone= decodeMessage(messageList.get(0));
                        VRDLogic vrdLogic=vrdLogics.get(i);
                        if(receiverTelephone.equals(vrdLogic.getTelephoneNumber())){
                            vrdLogic.increaseMessageCount();
                            messageList.remove(0);
                            Collections.rotate(messageList, -1);
                            exist=true;
                            break;
                        }
                    }
                    if(!exist){
                        messageList.remove(0);
                        Collections.rotate(messageList, -1);
                        try {
                            throw new StationNotFoundException();
                        } catch (StationNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }else {
                    messageList.remove(0);
                    Collections.rotate(messageList, -1);
                    try {
                        throw new StationNotFoundException();
                    } catch (StationNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public void setRight(){isLeft=false;}
    public void setLogic(Inter inter){logic=inter;}
}