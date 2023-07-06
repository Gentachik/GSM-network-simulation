import javax.swing.*;
import java.awt.*;
public class BTSstation extends JPanel {
    private Inter logic;
    private static int idCounter=1;
    private final int id;
    BTSstation() {
        super();
        this.setLayout(new BorderLayout());
        id=idCounter;
        idCounter++;
        JLabel stationNumberLabel = new JLabel("Station " + id);
        this.add(stationNumberLabel, BorderLayout.CENTER);

        JButton terminateButton = createTerminateButton();
        this.add(terminateButton, BorderLayout.SOUTH);
    }
    private JButton createTerminateButton(){
        JButton button = new JButton("Terminate");
        button.setBackground(Color.ORANGE);
        button.addActionListener(e -> {
            Container parentContainer = BTSstation.this.getParent();
            if (parentContainer instanceof JPanel parentPanel) {
                if(logic!=null){
                    logic.deleteBTS(id);
                }
                parentPanel.remove(BTSstation.this);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        });
        return button;
    }
    public int getID() {return id;}
    public void setLogic(Inter inter){logic=inter;}
}
