import javax.swing.*;
import java.awt.*;

public  class VRDstation extends JPanel implements MessageCountListener{
    private Inter logic;
    private VRDdelete VRDdelete;
    private static int idCounter=1;
    private final int id;
    private final JLabel label =new JLabel();
    public VRDstation() {
        super();
        id=idCounter;
        idCounter++;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setLayout(new BorderLayout());

        JButton terminate = addTerminateButton();
        this.add(terminate, BorderLayout.SOUTH);
        label.setText(String.valueOf(0));
        JCheckBox checkBox = addCheckBoxToClear();
        checkBox.setSelected(true);
        this.add(checkBox, BorderLayout.CENTER);
        this.add(label, BorderLayout.NORTH);
    }
    private JButton addTerminateButton() {
        JButton button = new JButton("Terminate");
        button.setBackground(Color.ORANGE);
        button.addActionListener(e -> {
            Container parentContainer = VRDstation.this.getParent();
            if (parentContainer != null) {
                if(VRDdelete!=null){
                    VRDdelete.deleteIdVRD(id);
                }
                parentContainer.remove(VRDstation.this);
                parentContainer.revalidate();
                parentContainer.repaint();
            }
        });
        return button;
    }
    private JCheckBox addCheckBoxToClear() {
        JCheckBox checkBox = new JCheckBox("Clear messages");
        checkBox.addActionListener(e -> {
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
        });
        return checkBox;
    }
    public int getId() {return id;}
    public void addLogic(VRDdelete logic) {VRDdelete=logic;}
    public void setLogic(Inter inter){logic=inter;}
    @Override
    public void setMessageCount(ChangeMessageCountEvent event) {
        label.setText(String.valueOf(event.getMessageCount()));
    }
}
