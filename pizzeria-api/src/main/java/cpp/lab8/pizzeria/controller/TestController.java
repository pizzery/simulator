package cpp.lab8.pizzeria.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cpp.lab8.pizzeria.simulation.DTO.OrderDTO;
import cpp.lab8.pizzeria.simulation.DTO.PizzaDTO;
import cpp.lab8.pizzeria.simulation.order.OrderState;
import cpp.lab8.pizzeria.simulation.order.PizzaState;
import cpp.lab8.pizzeria.socket.BroadcastSocket;

@RestController
@RequestMapping("/test")
public class TestController {
    private int lastOrder = 0;
    private int lastPizza = 0;

    private final BroadcastSocket socket;

    @Autowired
    public TestController(BroadcastSocket socket) {
        this.socket = socket;
    }

    private OrderState randOState() {
        Random rnd = new Random();
        OrderState result;
        switch(rnd.nextInt(3)) {
            case 0: result = OrderState.Idle; break;
            case 1: result = OrderState.InProgress; break;
            default: result = OrderState.Done; break;
        }
        return result;
    } 

    private PizzaState randPState() {
        Random rnd = new Random();
        PizzaState result;
        switch(rnd.nextInt(5)) {
            case 0: result = PizzaState.Idle; break;
            case 1: result = PizzaState.DoughMaking; break;
            case 2: result = PizzaState.Filling; break;
            case 3: result = PizzaState.Baking; break;
            default: result = PizzaState.Done; break;
        }
        return result;
    } 

    private void sendDTO(OrderDTO dto) {
        socket.broadcast(dto);
    }

    @GetMapping("/dto")
    public OrderDTO example(@RequestParam(defaultValue = "1") int pc,
                            @RequestParam(defaultValue = "1") int qc) {
        List<PizzaDTO> pizzas = new ArrayList<>();
        for(int i = 0; i < pc; i++) {
            pizzas.add(
                PizzaDTO.builder()
                    .pizzaId(++lastPizza)
                    .state(randPState())
                    .cookId(pc - i)
                    .build()
            );
        }
        OrderDTO dto = OrderDTO.builder()   
            .orderId(++lastOrder)
            .customerId(lastOrder)
            .queueId(new Random().nextInt(qc) + 1)
            .state(randOState())
            .pizzas(pizzas)
            .build();

        sendDTO(dto);
        return dto;
    }
}
