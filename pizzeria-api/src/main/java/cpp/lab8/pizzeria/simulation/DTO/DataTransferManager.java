package cpp.lab8.pizzeria.simulation.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cpp.lab8.pizzeria.simulation.pizza.Pizza;
import cpp.lab8.pizzeria.simulation.pizza.PizzaSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.DTO.OrderDTO.OrderDTOBuilder;
import cpp.lab8.pizzeria.simulation.customer.Customer;
import cpp.lab8.pizzeria.simulation.customer.CustomerSystem;
import cpp.lab8.pizzeria.simulation.order.Order;
import cpp.lab8.pizzeria.simulation.order.OrderSystem;
import cpp.lab8.pizzeria.socket.BroadcastSocket;

/**
 * Class, that is responsible for communication with frontend
 */
@Component
public class DataTransferManager {
    private final PizzeriaManager pizzeriaManager;
    private final BroadcastSocket broadcastSocket;

    @Autowired
    DataTransferManager(@Lazy PizzeriaManager pizzeriaManager, BroadcastSocket broadcastSocket) {
        this.pizzeriaManager = pizzeriaManager;
        this.broadcastSocket = broadcastSocket;
    }

    /**
     * Prepares DTO from object and sends it through the socket
     * if successfull
     * @param entity - entity for which data has to be prepared
     */
    public void sendEntity(Object entity) {
        Optional<OrderDTO> dto = prepareDTOFromEntity(entity);
        System.out.println(dto);
        if (dto.isPresent()) broadcastSocket.broadcast(dto.get());
    }

    /**
     * Creates Order data transfer object from any entity
     * @param entity - an entity, which is present in the system
     * @return Optional of OrderDTO
     */
    public synchronized Optional<OrderDTO> prepareDTOFromEntity(Object entity) {
        // prepare currently relevant systems
        OrderSystem os = pizzeriaManager.getOrderSystem();
        CustomerSystem cs = pizzeriaManager.getCustomerSystem();
        PizzaSystem ps = pizzeriaManager.getPizzaSystem();
        // TODO: consider other systems

        // get order object from entity to work with it further
        Order order;
        try {
            if (entity instanceof Order) order = (Order)entity;
            else if (entity instanceof Customer) order = os.getOrderById(((Customer)entity).getOrderId());
            else if (entity instanceof Pizza) order = os.getOrderById(((Pizza)entity).getOrderId());
            // TODO: consider other systems
            else throw new NullPointerException();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return Optional.ofNullable(null);
        }

        // collect pizzas
        List<PizzaDTO> pizzas = new ArrayList<>();
        for (Pizza pizza:
             ps.getPizzasByOrderId(order.getId())) {
                PizzaDTO pizzaDTO = PizzaDTO.builder()
                        .pizzaId(pizza.getPizzaId())
                        .cookId(pizza.getCookId())
                        .state(pizza.getState())
                        .build();
                pizzas.add(pizzaDTO);
        }

        // get other entities from order
        Customer customer = cs.getCustomerByOrderId(order.getId());
        // TODO: consider other entities

        // build final object
        OrderDTOBuilder orderBuilder = OrderDTO.builder()
            .orderId(order.getId())
            .customerId(customer == null ? null : customer.getId())
            .queueId(null)    // TODO: get current queue of pizza using Queue system
            .state(null)        // TODO: get current state of pizza using Cook system
            .pizzas(pizzas);

        return Optional.of(orderBuilder.build());
    }
}
