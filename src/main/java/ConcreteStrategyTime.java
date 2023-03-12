import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        int minTime = Integer.MAX_VALUE;
        for (Server s : servers) {
            if (s.getWaitingPeriod().get() < minTime) {
                minTime = s.getWaitingPeriod().get();
            }
        }
        for (Server s : servers) {
            if (s.getWaitingPeriod().get() == minTime) {
                s.addTask(t);
                break;
            }
        }
    }
}
