import javax.swing.*;
import java.awt.*;
public  class BSCstation extends JPanel {
    private static int stationNumber=1;
    private final int id;
    BSCstation() {
        super();
        this.setLayout(new BorderLayout());
        id=stationNumber;
        stationNumber++;
        JLabel stationNumberLabel = new JLabel("Station " + id);
        this.add(stationNumberLabel, BorderLayout.CENTER);

        JButton terminateButton = createTerminateButton();
        this.add(terminateButton, BorderLayout.SOUTH);
    }
    private JButton createTerminateButton(){
        JButton button = new JButton("Terminate");
        button.setBackground(Color.ORANGE);
        button.addActionListener(e -> {
            Container parentContainer = BSCstation.this.getParent();
            if (parentContainer instanceof JPanel parentPanel) {
                parentPanel.remove(BSCstation.this);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        });
        return button;
    }
    public int getId() {return id;}
}
