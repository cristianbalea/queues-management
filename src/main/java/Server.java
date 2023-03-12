import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int serverID;
    private Task currentTask;

    public Server() {
        tasks = new PriorityBlockingQueue<>();
        waitingPeriod = new AtomicInteger();
    }

    public void addTask(Task newTask) {
        try {
            tasks.put(newTask);
            waitingPeriod.addAndGet(newTask.getServiceTime());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = tasks.take();
                currentTask = task;
                int i = task.getServiceTime();
                while (i != 0) {
                    waitingPeriod.decrementAndGet();
                    i--;
                    Thread.sleep(1000);
                }
                currentTask = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public int getNrOfTasks() {
        return tasks.size();
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public Task getCurrentTask() {
        return currentTask;
    }
}
