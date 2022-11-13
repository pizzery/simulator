package cpp.lab8.pizzeria.simulation.DTO;

import cpp.lab8.pizzeria.simulation.pizza.PizzaState;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object for pizza
 */
@Data
@Builder
public class PizzaDTO {
    private Integer pizzaId;
    private Integer cookId;
    private PizzaState state;
}
