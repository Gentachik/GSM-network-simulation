import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VBDstation extends JPanel {
    private VBDInteface logic;
    private VBDdelete deleteStation;
    private static int numberCounter=1;
    private int id;
    private String message;
    VBDstation(String message){
        super();
        id=numberCounter;
        numberCounter++;
        this.message=message;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setLayout(new BorderLayout());

        JButton terminate= addTerminateButton();
        this.add(terminate, BorderLayout.SOUTH);

        JSlider slider = setSlider();
        this.add(slider,BorderLayout.EAST);

        JComboBox indicatorBox = addIndicatorBox();
        this.add(indicatorBox, BorderLayout.NORTH);

        JTextField numberVBD=new JTextField(String.valueOf(id));
        numberVBD.setEditable(false);
        this.add(numberVBD, BorderLayout.CENTER);
    }
    private JSlider setSlider(){
        JSlider slider = new JSlider(JSlider.VERTICAL, 1, 10, 5);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(logic!=null) {
                    logic.setSpeed(slider.getValue());
                }
            }
        });
        return slider;
    }
    private JButton addTerminateButton(){
        JButton button = new JButton("Terminate");
        button.setBackground(Color.ORANGE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parentContainer = VBDstation.this.getParent();
                if (parentContainer != null) {
                    if(deleteStation!=null){
                        deleteStation.deleteIdVBD(id);
                    }
                    parentContainer.remove(VBDstation.this);
                    parentContainer.revalidate();
                    parentContainer.repaint();
                }
            }
        });
        return button;
    }
    private JComboBox addIndicatorBox(){
        String[] options = {"ACTIVE","WAITING"};
        JComboBox comboBox = new JComboBox<>(options);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                if(logic!=null) {
                    if (selectedOption.equals("WAITING")) {
                        logic.setStatus(false);
                    } else {
                        logic.setStatus(true);
                    }
                }
            }
        });
        return comboBox;
    }
    public String getMessage(){return message;}
    public int getId() {return id;}
    public void addLogic(VBDdelete logic) {deleteStation=logic;}
    public void setVBDLogic(VBDInteface logic){
        this.logic=logic;
    }
}