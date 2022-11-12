package cpp.lab8.pizzeria.simulation.DTO;

import java.util.List;

import cpp.lab8.pizzeria.simulation.order.OrderState;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object for order
 * <p>Atom of communication with frontend</p>
 */
@Data
@Builder
public class OrderDTO {
    private Integer orderId;
    private Integer customerId;
    private Integer queueId;
    private OrderState state;
    private List<PizzaDTO> pizzas;
}
