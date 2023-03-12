import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        int minQ = Integer.MAX_VALUE;
        for (Server s : servers) {
            if (s.getNrOfTasks() < minQ) {
                minQ = s.getNrOfTasks();
            }
        }
        for (Server s : servers) {
            if (minQ == s.getNrOfTasks()) {
                s.addTask(t);
                break;
            }
        }
    }
}
