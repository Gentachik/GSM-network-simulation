import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public  class VRDstation extends JPanel implements MessageCounter{
    private Inter logic;
    private VRDdelete VRDdelete;
    private int receivedMessages;
    private static int idCounter=1;
    private boolean isSelected=true;
    private int id;
    private JCheckBox checkBox;
    private JLabel label =new JLabel();
    public VRDstation() {
        super();
        id=idCounter;
        idCounter++;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setLayout(new BorderLayout());

        JButton terminate = addTerminateButton();
        this.add(terminate, BorderLayout.SOUTH);

        checkBox = addCheckBoxToClear("Clear messages");
        checkBox.setSelected(true);
        this.add(checkBox, BorderLayout.CENTER);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    label.setText(String.valueOf(receivedMessages));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        this.add(label, BorderLayout.NORTH);
    }
    private JButton addTerminateButton() {
        JButton button = new JButton("Terminate");
        button.setBackground(Color.ORANGE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parentContainer = VRDstation.this.getParent();
                if (parentContainer != null) {
                    if(VRDdelete!=null){
                        VRDdelete.deleteIdVRD(id);
                    }
                    parentContainer.remove(VRDstation.this);
                    parentContainer.revalidate();
                    parentContainer.repaint();
                }
            }
        });
        return button;
    }
    private JCheckBox addCheckBoxToClear(String name) {
        JCheckBox checkBox = new JCheckBox(name);
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    if(logic!=null) {
                        for (int i = 0; i < logic.getVRDList().size(); i++) {
                            VRDLogic vrdLogic = logic.getVRDList().get(i);
                            if (vrdLogic.getId() == id) {
                                vrdLogic.setRefresh(true);
                            }
                        }
                    }
                }else {
                    if(logic!=null) {
                        for (int i = 0; i < logic.getVRDList().size(); i++) {
                            VRDLogic vrdLogic = logic.getVRDList().get(i);
                            if (vrdLogic.getId() == id) {
                                vrdLogic.setRefresh(false);
                            }
                        }
                    }
                }
            }
        });
        return checkBox;
    }
    public int getId() {return id;}
    public void addLogic(VRDdelete logic) {VRDdelete=logic;}
    public void setLogic(Inter inter){logic=inter;}
    @Override
    public void getMessageCounter(int value) {
        receivedMessages=value;
    }
}
