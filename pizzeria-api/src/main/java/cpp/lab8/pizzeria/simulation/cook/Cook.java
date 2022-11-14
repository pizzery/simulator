package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.order.Order;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Data
public class Cook {
    // queue id
    @NonNull
    private Integer id;

    private Integer pizzaId;

    private CookType cookType;
}