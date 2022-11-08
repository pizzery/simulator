package cpp.lab8.pizzeria.simulation.order;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Order entity
 */
@Data
public class Order {
    // order id
    @NonNull
    private Integer id;

    private Integer minTime;

    @Setter
    @Getter
    private String orderStatus;

    // TODO: other customer properties
}

