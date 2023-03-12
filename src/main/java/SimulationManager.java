import javax.swing.plaf.TableHeaderUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class SimulationManager implements Runnable {
    public int timeLimit = 15;
    public int maxProcessingTime = 4;
    public int minProcessingTime = 2;
    public int maxArrivalTime = 10;
    public int minArrivalTime = 2;
    public int numberOfServers = 2;
    public int numberOfClients = 4;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private SimulationView simulationView;
    private List<Task> generatedTask = new ArrayList<>();

    private boolean start;

    public SimulationManager(int timeLimit, int clients, int queues, int minArr, int maxArr, int minSer, int maxSer, SelectionPolicy policy) {
        this.timeLimit = timeLimit;
        numberOfClients = clients;
        numberOfServers = queues;
        minArrivalTime = minArr;
        maxArrivalTime = maxArr;
        minProcessingTime = minSer;
        maxProcessingTime = maxSer;
        selectionPolicy = policy;
        simulationView = new SimulationView();
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);

        generateNRandomTasks();
        //System.out.println(generatedTask.toString());
    }

    private void generateNRandomTasks() {
        for (int i = 1; i <= numberOfClients; i++) {
            Task t = new Task(i, i, i);
            Random rn = new Random();
            t.setServiceTime(rn.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime);
            t.setArrivalTime(rn.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime);
            generatedTask.add(t);
        }
        Collections.sort(generatedTask);
    }

    @Override
    public void run() {
        int peakTime = 0, nrOfTasks = 0, nrMaxOfTasks = -1, totalOfTasks = 0;
        float avgServiceTime = 0.0f;
        try {
            PrintWriter writer = new PrintWriter("C:\\Users\\balea\\Documents\\Facultate\\TP\\PT2022_30221_Balea_Cristian_Constantin_Assignment_2\\file.txt");
            int currentTime = 0;
            int finished = 0;
            while (currentTime < timeLimit) {
                List<Task> toRemove = new ArrayList<>();
                for (Task t : generatedTask) {
                    if (t.getArrivalTime() == currentTime) {
                        totalOfTasks++;
                        nrOfTasks++;
                        avgServiceTime += t.getServiceTime();
                        if (nrOfTasks > nrMaxOfTasks) {
                            nrMaxOfTasks = nrOfTasks;
                            peakTime = currentTime;
                        }
                        // System.out.println(t.getId());
                        scheduler.dispatchTask(t);
                        toRemove.add(t);
                    }
                }
                generatedTask.removeAll(toRemove);
                // afisarea
                String str = "Current time: " + currentTime + "\nWaiting tasks:\n";
                System.out.println("Current time: " + currentTime + "\nWaiting tasks:");
                writer.write("Current time: " + currentTime + "\nWaiting tasks:\n");
                for (Task t : generatedTask) {
                    System.out.println("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ")");
                    str += "(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ")\n";
                    writer.write("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ")\n");
                }
                for (Server s : scheduler.getServers()) {
                    Task currentTask;
                    if (s.getCurrentTask() != null && s.getCurrentTask().getServiceTime() > 0) {
                        currentTask = s.getCurrentTask();
                        System.out.print("Coada " + s.getServerID() + ": ");
                        writer.write("Coada " + s.getServerID() + ": ");
                        str += ("Coada " + s.getServerID() + ": ");
                        System.out.print("(" + currentTask.getId() + ", " + currentTask.getArrivalTime() + ", " + currentTask.getServiceTime() + ") ");
                        writer.write("(" + currentTask.getId() + ", " + currentTask.getArrivalTime() + ", " + currentTask.getServiceTime() + ") ");
                        str += "(" + currentTask.getId() + ", " + currentTask.getArrivalTime() + ", " + currentTask.getServiceTime() + ") ";
                        currentTask.setServiceTime(currentTask.getServiceTime() - 1);
                    } else if (s.getWaitingPeriod().get() == 0) {
                        System.out.print("Coada " + s.getServerID() + ": closed.");
                        str += "Coada " + s.getServerID() + ": closed.";
                        writer.write("Coada " + s.getServerID() + ": closed.");
                    } else {
                        System.out.print("Coada " + s.getServerID() + ": ");
                        str += "Coada " + s.getServerID() + ": ";
                        writer.write("Coada " + s.getServerID() + ": ");
                    }
                    for (Task t : s.getTasks()) {
                        System.out.print("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ") ");
                        str += "(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ") ";
                        writer.write("(" + t.getId() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ") ");
                    }
                    System.out.println();
                    str += "\n";
                    writer.write("\n");
                }
                System.out.println();
                str += "\n";
                simulationView.simulationText(str);
                writer.write("\n");
                currentTime++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int ok = 1;
                for (Server s : scheduler.getServers()) {
                    if (s.getCurrentTask() != null) {
                        ok = 0;
                    }
                }
                if (finished == 1) {
                    break;
                }
                if (generatedTask.isEmpty() && ok == 1) {
                    finished = 1;
                }
            }
            System.out.println("PeakTime: " + peakTime);
            writer.write("PeakTime: " + peakTime + "\n");
            simulationView.setResults1("PeakTime " + peakTime + ".");
            avgServiceTime /= totalOfTasks;
            System.out.println("Average service time: " + avgServiceTime);
            writer.write("Average service time: " + avgServiceTime);
            simulationView.setResults2("Average service time: " + avgServiceTime + ".");
            writer.close();
        } catch (IOException e) {
            System.err.println("Exception: " + e);
        }
    }
}
