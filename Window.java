import javax.swing.*;
import java.awt.*;
public class Window {
    private Inter logic;
    private final BSCContainer BSC;
    private final BTSLayer leftBTS;
    private final BTSLayer rightBTS;

    public Window() {
        JFrame window = new JFrame("GSM network simulation");
        window.setSize(800, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel(new GridLayout(1, 5));

        JPanel VBD = createVBD();
        JPanel VRD = createVRD();
        BSC = createBSC();
        leftBTS = createLeftBTS();
        rightBTS = createRightBTS();

        container.add(VBD);
        container.add(new JScrollPane(leftBTS));
        container.add(BSC);
        container.add(new JScrollPane(rightBTS));
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

        JScrollPane scrollPane = new JScrollPane(labelsPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        addingButton.addActionListener(e -> {
            VRDstation label = new VRDstation();
            labelsPanel.add(label);
            if (logic != null) {
                logic.getVRD(label);
            }
            panel.revalidate();
        });
        return panel;
    }

    private BSCContainer createBSC() {
        return new BSCContainer();
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

        addingButton.addActionListener(e -> {
            String message = JOptionPane.showInputDialog("Enter your message:");
            if (message != null && !message.isEmpty()) {
                VBDstation station = new VBDstation(message);
                if (logic != null) {
                    logic.getVBD(station);
                }
                labelsPanel.add(station);
                panel.revalidate();
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
