package cpp.lab8.pizzeria.simulation.queue;

import cpp.lab8.pizzeria.simulation.customer.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
public class Queue {
    // queue id
    @NonNull
    private Integer id;

    // customers in particular queue
    @Getter
    @Setter
    private java.util.Queue<Customer> customersInQueue;
}
