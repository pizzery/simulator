package cpp.lab8.pizzeria.simulation.pizza;

import lombok.Data;
import lombok.NonNull;

/**
 * Pizza entity
 */
@Data
public class Pizza {
    @NonNull
    private Integer pizzaId;

    @NonNull
    private Integer orderId;

    private Integer cookId;

    private PizzaState state;

    @NonNull
    private Integer menuItem;
}
