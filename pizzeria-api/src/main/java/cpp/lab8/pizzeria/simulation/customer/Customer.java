package cpp.lab8.pizzeria.simulation.customer;

import lombok.Data;
import lombok.NonNull;

/**
 * Customer entity
 */
@Data
public class Customer {
    // customer id
    @NonNull
    private Integer id;

    // id of the order, attached to customer
    private Integer orderId;
}
