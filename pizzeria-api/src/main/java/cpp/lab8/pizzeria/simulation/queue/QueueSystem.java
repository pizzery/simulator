package cpp.lab8.pizzeria.simulation.queue;

import java.util.*;
import java.util.stream.Collectors;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.customer.Customer;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.socket.BroadcastSocket;

@Component
public class QueueSystem {
    @Autowired
    private DataTransferManager dtm;

    // list of queues in the system
    private List<Queue> queues;

    public void createQueues(int n){
        queues = new ArrayList<>();

        for(int i = 0; i < n; i++){
            queues.add(new Queue(i + 1));
            queues.get(i).setCustomersInQueue(new LinkedList<>());
        }
    }

    public void assignCustomerToShortestQueue(Customer customer) {
        Queue queue = getShortestQueue();
        queue.getCustomersInQueue().add(customer);
    }

    public Queue getShortestQueue() {
        int minSize = queues.stream().min(Comparator.comparingInt(q -> q.getCustomersInQueue().size())).get().getCustomersInQueue().size();
        List<Queue> minQueues = queues.stream().filter(q -> q.getCustomersInQueue().size() == minSize).collect(Collectors.toList());
        return minQueues.get(new Random().nextInt(0, minQueues.size()));
    }

    public synchronized Queue getQueueByCustomer(Customer customer) throws NullPointerException {
        return this.queues.stream()
                .filter(queue -> queue.getCustomersInQueue().contains(customer))
                .findFirst().get();
    }
}
