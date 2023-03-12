import javax.swing.*;
import java.awt.*;

public class SimulationView {
    private final JFrame frame = new JFrame("SIMULATION");
    private final JPanel panel = new JPanel();
    private JTextArea display = new JTextArea();
    private JTextField results1 = new JTextField();
    private JTextField results2 = new JTextField();
    private JScrollPane scroll = new JScrollPane(display);
    private Font font = new Font("Arial", Font.BOLD, 10);

    public SimulationView() {
        display.setFont(font);
        display.setText("Servus");
        display.setEditable(false);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scroll.setBounds(50, 10, 1100, 600);
        results1.setBounds(50, 640, 400, 30);
        results2.setBounds(50, 680, 400, 30);

        results1.setFont(font);
        results2.setFont(font);

        results1.setEditable(false);
        results2.setEditable(false);

        panel.add(results1);
        panel.add(results2);
        panel.add(scroll);

        panel.setLayout(null);
        frame.setSize(1200, 800);
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SimulationView simulationView = new SimulationView();
    }

    public void simulationText(String s) {
        display.setText(s);
    }
    public void setResults1(String s) {
        results1.setText(s);
    }
    public void setResults2(String s) {
        results2.setText(s);
    }
}
