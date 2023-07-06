import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BSCContainer extends JPanel {
    private Inter logic;
    private JPanel labelsPanel;
    BSCContainer(){
        super();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setLayout(new BorderLayout());

        JButton addButton = new JButton("Add");
        addButton.setBackground(Color.GREEN);

        labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(labelsPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BSCLayer newPanel = new BSCLayer();

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                if(logic!=null){
                    logic.getBSCLayer(newPanel);
                }
                labelsPanel.add(newPanel, gbc);
                labelsPanel.revalidate();
                labelsPanel.repaint();
            }
        });

        JButton removeButton = new JButton("Remove");
        removeButton.setBackground(Color.RED);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int componentCount = labelsPanel.getComponentCount();
                if (componentCount > 1) {
                    Component lastPanel = labelsPanel.getComponent(componentCount - 1);
                    if(logic!=null) {
                        BSCLayer layer=(BSCLayer) lastPanel;
                        logic.deleteBSCLayer(layer);
                    }
                    labelsPanel.remove(lastPanel);
                    labelsPanel.revalidate();
                    labelsPanel.repaint();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
        run();
    }
    private void run(){
        new Thread(() -> {
            while (true) {
                try {
                    if(logic!=null) {
                        BSCLayer firstPanel = new BSCLayer();
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.fill = GridBagConstraints.BOTH;
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        labelsPanel.add(firstPanel, gbc);
                        logic.getBSCLayer(firstPanel);
                        break;
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void setLogic(Inter inter) {logic=inter;}
}
