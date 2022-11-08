package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.order.Order;
import lombok.Data;
import lombok.NonNull;

@Data
public class Cook {
    // queue id
    @NonNull
    private Integer id;

    // order to cook
    private Order orderToCook;
}
