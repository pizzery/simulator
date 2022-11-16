package cpp.lab8.pizzeria.simulation.queue;

import java.util.*;
import java.util.stream.Collectors;

import cpp.lab8.pizzeria.simulation.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class QueueSystem {
    // list of queues in the system
    private List<Queue> queues;

    public void createQueues(int n){
        queues = new ArrayList<>();

        for(int i = 0; i < n; i++){
            queues.add(new Queue(i + 1));
            queues.get(i).setCustomersInQueue(new LinkedList<>());
        }
    }

    public synchronized void clear() {
        this.queues.clear();
    }

    public void assignCustomerToShortestQueue(Customer customer) {
        Queue queue = getShortestQueue();
        queue.getCustomersInQueue().add(customer);
    }

    public Queue getShortestQueue() {
        int minSize = queues.stream().min(Comparator.comparingInt(q -> q.getCustomersInQueue().size())).get().getCustomersInQueue().size();
        List<Queue> minQueues = queues.stream().filter(q -> q.getCustomersInQueue().size() == minSize).collect(Collectors.toList());
        return minQueues.get(new Random().nextInt(minQueues.size()));
    }

    public synchronized Queue getQueueByCustomer(Customer customer) throws NullPointerException {
        return this.queues.stream()
                .filter(queue -> queue.getCustomersInQueue().contains(customer))
                .findFirst().get();
    }
}
