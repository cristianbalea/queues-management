import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View implements ActionListener {
    private final JFrame frame = new JFrame("SIMULATION SETUP");
    private final JPanel panel = new JPanel();
    private final JTextField textNrOfClients = new JTextField();
    private final JTextField textNrOfQueues = new JTextField();
    private final JTextField textSimulationInterval = new JTextField();
    private final JTextField textMinArrivalTime = new JTextField();
    private final JTextField textMaxArrivalTime = new JTextField();
    private final JTextField textMinServiceTime = new JTextField();
    private final JTextField textMaxServiceTime = new JTextField();
    private final JLabel labelNrOfClients = new JLabel("Number of clients");
    private final JLabel labelNrOfQueues = new JLabel("Number of queues");
    private final JLabel labelSimulationInterval = new JLabel("Simulation interval");
    private final JLabel labelMinArrivalTime = new JLabel("Minimum arrival time");
    private final JLabel labelMaxArrivalTime = new JLabel("Maximum arrival time");
    private final JLabel labelMinServiceTime = new JLabel("Minimum service time");
    private final JLabel labelMaxServiceTime = new JLabel("Maximum service time");
    private final JButton btnSimulate = new JButton("SIMULATE");
    private final JRadioButton btnTime = new JRadioButton("SHORTEST_TIME");
    private final JRadioButton btnQueue = new JRadioButton("SHORTEST_QUEUE");

    View() {
        labelNrOfClients.setBounds(30, 10, 200, 30);
        textNrOfClients.setBounds(210, 10, 200, 30);
        labelNrOfQueues.setBounds(30, 50, 200, 30);
        textNrOfQueues.setBounds(210, 50, 200, 30);
        labelSimulationInterval.setBounds(30, 90, 200, 30);
        textSimulationInterval.setBounds(210, 90, 200, 30);
        labelMinArrivalTime.setBounds(30, 130, 200, 30);
        textMinArrivalTime.setBounds(210, 130, 200, 30);
        labelMaxArrivalTime.setBounds(30, 160, 200, 30);
        textMaxArrivalTime.setBounds(210, 160, 200, 30);
        labelMinServiceTime.setBounds(30, 200, 200, 30);
        textMinServiceTime.setBounds(210, 200, 200, 30);
        labelMaxServiceTime.setBounds(30, 230, 200, 30);
        textMaxServiceTime.setBounds(210, 230, 200, 30);
        btnTime.setBounds(30, 270, 150, 30);
        btnQueue.setBounds(180, 270, 150, 30);
        btnSimulate.setBounds(100, 320, 200, 30);

        btnSimulate.addActionListener(this);

        ButtonGroup radios = new ButtonGroup();
        radios.add(btnTime);
        radios.add(btnQueue);
        btnTime.setSelected(true);

        panel.add(labelNrOfClients);
        panel.add(textNrOfClients);
        panel.add(labelNrOfQueues);
        panel.add(textNrOfQueues);
        panel.add(labelSimulationInterval);
        panel.add(textSimulationInterval);
        panel.add(labelMinServiceTime);
        panel.add(textMinServiceTime);
        panel.add(labelMinArrivalTime);
        panel.add(textMinArrivalTime);
        panel.add(labelMaxServiceTime);
        panel.add(textMaxServiceTime);
        panel.add(labelMaxArrivalTime);
        panel.add(textMaxArrivalTime);
        panel.add(btnSimulate);
        panel.add(btnTime);
        panel.add(btnQueue);

        panel.setLayout(null);
        frame.setVisible(true);
        frame.setSize(450, 400);
        frame.setContentPane(panel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        View view = new View();
    }

    public int getTextNrOfClients() {
        return Integer.parseInt(textNrOfClients.getText());
    }

    public int getTextNrOfQueues() {
        return Integer.parseInt(textNrOfQueues.getText());
    }

    public int getTextSimulationInterval() {
        return Integer.parseInt(textSimulationInterval.getText());
    }

    public int getTextMinArrivalTime() {
        return Integer.parseInt(textMinArrivalTime.getText());
    }

    public int getTextMaxArrivalTime() {
        return Integer.parseInt(textMaxArrivalTime.getText());
    }

    public int getTextMinServiceTime() {
        return Integer.parseInt(textMinServiceTime.getText());
    }

    public int getTextMaxServiceTime() {
        return Integer.parseInt(textMaxServiceTime.getText());
    }

    public SelectionPolicy getPolicy() {
        if (btnTime.isSelected()) {
            return SelectionPolicy.SHORTEST_TIME;
        }
        return SelectionPolicy.SHORTEST_QUEUE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        SimulationManager simulationManager = new SimulationManager(getTextSimulationInterval(), getTextNrOfClients(), getTextNrOfQueues(), getTextMinArrivalTime(),
                getTextMaxArrivalTime(), getTextMinServiceTime(), getTextMaxServiceTime(), getPolicy());
        Thread t = new Thread(simulationManager);
        t.start();
    }
}
