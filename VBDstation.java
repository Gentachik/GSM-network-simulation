import javax.swing.*;
import java.awt.*;
public class VBDstation extends JPanel {
    private VBDInteface logic;
    private VBDdelete deleteStation;
    private static int numberCounter=1;
    private final int id;
    private final String message;
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
        slider.addChangeListener(e -> {
            if(logic!=null) {
                logic.setSpeed(slider.getValue());
            }
        });
        return slider;
    }
    private JButton addTerminateButton(){
        JButton button = new JButton("Terminate");
        button.setBackground(Color.ORANGE);
        button.addActionListener(e -> {
            Container parentContainer = VBDstation.this.getParent();
            if (parentContainer != null) {
                if(deleteStation!=null){
                    deleteStation.deleteIdVBD(id);
                }
                parentContainer.remove(VBDstation.this);
                parentContainer.revalidate();
                parentContainer.repaint();
            }
        });
        return button;
    }
    private JComboBox addIndicatorBox(){
        String[] options = {"ACTIVE","WAITING"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();
            if(logic!=null) {
                assert selectedOption != null;
                logic.setStatus(!selectedOption.equals("WAITING"));
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