import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Window {
    private Inter logic;
    private JPanel VBD;
    private JPanel VRD;
    private BSCContainer BSC;
    private BTSLayer leftBTS;
    private BTSLayer rightBTS;
    private JFrame window;

    public Window() {
        window = new JFrame("Project 3");
        window.setSize(800, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel(new GridLayout(1, 5));

        VBD = createVBD();
        VRD = createVRD();
        BSC = createBSC();
        leftBTS = createLeftBTS();
        rightBTS = createRightBTS();

        container.add(VBD);
        container.add(new JScrollPane(leftBTS)); // Wrap leftBTS panel with JScrollPane
        container.add(BSC);
        container.add(new JScrollPane(rightBTS)); // Wrap rightBTS panel with JScrollPane
        container.add(VRD);

        window.setContentPane(container);
        window.setVisible(true);
    }

    private JPanel createVRD() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setLayout(new BorderLayout());

        JButton addingButton = new JButton("Add VRD");
        addingButton.setBackground(Color.GREEN);
        panel.add(addingButton, BorderLayout.SOUTH);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(labelsPanel); // Wrap labelsPanel with a JScrollPane
        panel.add(scrollPane, BorderLayout.CENTER);

        addingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VRDstation label = new VRDstation();
                labelsPanel.add(label);
                if (logic != null) {
                    logic.getVRD(label);
                }
                panel.revalidate();
            }
        });
        return panel;
    }

    private BSCContainer createBSC() {
        BSCContainer panel = new BSCContainer();
        return panel;
    }

    private BTSLayer createLeftBTS() {
        BTSLayer panel = new BTSLayer();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    private BTSLayer createRightBTS() {
        BTSLayer panel = new BTSLayer();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setRight();
        return panel;
    }

    private JPanel createVBD() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setLayout(new BorderLayout());

        JButton addingButton = new JButton("Add VBD");
        addingButton.setBackground(Color.GREEN);
        panel.add(addingButton, BorderLayout.SOUTH);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(labelsPanel); // Wrap labelsPanel with a JScrollPane
        panel.add(scrollPane, BorderLayout.CENTER);

        addingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = JOptionPane.showInputDialog("Enter your message:");
                if (message != null && !message.isEmpty()) {
                    VBDstation station = new VBDstation(message);
                    if (logic != null) {
                        logic.getVBD(station);
                    }
                    labelsPanel.add(station);
                    panel.revalidate();
                }
            }
        });

        return panel;
    }

    public void setLogic(Inter inter) {
        logic = inter;
        leftBTS.setLogic(logic);
        BSC.setLogic(logic);
        rightBTS.setLogic(logic);
    }
}
