import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class VBDLogic implements VBDInteface {
    private Inter logic;
    private int id;
    private int speed=5;
    private boolean go=true;
    private String message;
    private int messageSent;
    private String telephoneNumber;
    private boolean isActive=true;
    public VBDLogic(VBDstation station) {
        id=station.getId();
        message=station.getMessage();
        setTelephoneNumber(id);
        run();
    }
    public int getId() {return id;}
    public void setSpeed(int speed) { this.speed=speed;}
    private void setTelephoneNumber(int i) {
        telephoneNumber = "+" +i;
        if(Integer.toString(i).length()<11){
            for (int j = 0; j < 11-Integer.toString(i).length(); j++) {
                telephoneNumber += "0";
            }
        }
    }
    @Override
    public void setStatus(boolean status) {
        this.isActive=status;
    }
    public int getSpeed() {return speed;}
    private void run(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (go) {
                    if (isActive) {
                        if(logic!=null){
                            if(!logic.getVRDList().isEmpty()) {
                                ArrayList<BTSLogic> btsList=logic.getBTSLeftList();
                                int index=0;
                                int minMessage=5;
                                for (int i=0;i< btsList.size();i++){
                                    BTSLogic btsLogic=btsList.get(i);
                                    if(btsLogic.getMessageCount()<minMessage){
                                        index=i;
                                        minMessage= btsLogic.getMessageCount();
                                    }
                                }
                                BTSLogic workBTS=btsList.get(index);
                                workBTS.takeMessage(readySMS());
                                messageSent++;
                            }
                        }
                    }
                    try {
                        Thread.sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();
    }
    public void terminateStation() {go=false;}
    public void setLogic(Inter inter){logic=inter;}
    private String encodeNumber(String number) {
        String result = "";
        number = number.replace("+", "");
        if (number.length() % 2 != 0) {
            number = number + "F";
        }
        int i = 0;
        while (i < number.length()) {
            result += String.valueOf(number.charAt(i + 1)) + String.valueOf(number.charAt(i));
            i += 2;
        }
        return result;
    }
    private String encodeMessage(String message) {
        StringBuilder result = new StringBuilder();

        byte[] arr = message.getBytes(StandardCharsets.US_ASCII);
        int i = 1;
        while (i < arr.length) {
            int j = arr.length - 1;
            while (j >= i) {
                byte firstBit = (byte) ((arr[j] % 2) > 0 ? 0x80 : 0x00);
                arr[j - 1] = (byte) ((arr[j - 1] & 0x7f) | firstBit);
                arr[j] = (byte) (arr[j] >> 1);
                j--;
            }
            i++;
        }
        i = 0;
        while (i < arr.length && arr[i] != 0) {
            result.append(String.format("%02X", arr[i]));
            i++;
        }
        return result.toString();
    }
    private static String decodeMessage(String encodeMessage){
        String result;
        result=encodeMessage.substring(0, 36);
        result=result.substring(20);
        result=decodeNumber(result);
        return result;
    }
    private static String decodeNumber(String encodedNumber) {
        String result = "";
        int i = 0;
        while (i < encodedNumber.length()) {
            result += String.valueOf(encodedNumber.charAt(i + 1)) + String.valueOf(encodedNumber.charAt(i));
            i += 2;
        }
        if (result.endsWith("F")) {
            result = result.substring(0, result.length() - 1);
        }
        result= result.substring(4);
        result = "+" + result;
        return result;
    }
    private String readySMS(){
        ArrayList<VRDLogic> vrdList= logic.getVRDList();
        Random random = new Random();
        int randomIndex = random.nextInt(vrdList.size());
        VRDLogic randomVRD = vrdList.get(randomIndex);

        String encodedSMS;
        String SCA = String.format("%02X", (encodeNumber(telephoneNumber).length() / 2 + 1)) + "91" + encodeNumber(telephoneNumber);
        String FO = "04";
        String OA = String.format("%02X", randomVRD.getTelephoneNumber().length()-1) + "91" + encodeNumber(randomVRD.getTelephoneNumber());
        String PI = "00";
        String DCS = "00";
        String UDL = String.format("%02X", message.length());
        String UDM = encodeMessage(message);

        encodedSMS=SCA+FO+OA+PI+DCS+UDL+UDM;
        String octets=String.valueOf(encodedSMS.length()/2-1);
        encodedSMS=octets+encodedSMS;
        return encodedSMS;
    }
    public String getMessage() {return message;}
    public int getMessageSent() {return messageSent;}
}
