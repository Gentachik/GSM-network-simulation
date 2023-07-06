import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public  class BSCstation extends JPanel {
    private JLabel stationNumberLabel;
    private static int stationNumber=1;
    private int id;
    BSCstation() {
        super();
        this.setLayout(new BorderLayout());
        id=stationNumber;
        stationNumber++;
        stationNumberLabel = new JLabel("Station " + id);
        this.add(stationNumberLabel, BorderLayout.CENTER);

        JButton terminateButton = createTerminateButton();
        this.add(terminateButton, BorderLayout.SOUTH);
    }
    private JButton createTerminateButton(){
        JButton button = new JButton("Terminate");
        button.setBackground(Color.ORANGE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parentContainer = BSCstation.this.getParent();
                if (parentContainer instanceof JPanel) {
                    JPanel parentPanel = (JPanel) parentContainer;
                    parentPanel.remove(BSCstation.this);
                    parentPanel.revalidate();
                    parentPanel.repaint();
                }
            }
        });
        return button;
    }
    public int getId() {return id;}
}
