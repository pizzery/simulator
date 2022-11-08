package cpp.lab8.pizzeria.simulation.queue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import cpp.lab8.pizzeria.simulation.customer.Customer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.socket.BroadcastSocket;

@Component
public class QueueSystem {
    // socket to broadcast modifications
    @Autowired
    private BroadcastSocket socket;

    // list of queues in the system
    @Getter
    private List<Queue> queues;

    public void createQueues(int n){
        queues = new ArrayList<>();

        for(int i = 0; i < n; i++){
            queues.add(new Queue(i + 1));
            queues.get(i).setCustomersInQueue(new LinkedList<Customer>());

            for(int j = 0; j < 2; j++){
                queues.get(i).getCustomersInQueue().add(new Customer(j + 1));
            }
        }
    }

    public void assignCustomer(Customer customer, int queueId) {
        Queue queue = getQueue(queueId);
        queue.getCustomersInQueue().add(customer);
        socket.broadcastUpdate("queue", queue);
    }

    public void assignCustomerToShortestQueue(Customer customer) {
        Queue queue = getShortestQueue();
        queue.getCustomersInQueue().add(customer);
        socket.broadcastUpdate("queue", queue);
    }

    public Queue getQueue(int id) {
        return queues.stream().filter(c -> c.getId().equals(id)).findFirst().get();
    }

    public Queue getShortestQueue() {
        return queues.stream().max(Comparator.comparingInt(q -> q.getCustomersInQueue().size())).get();
    }
}
