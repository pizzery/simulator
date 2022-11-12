package cpp.lab8.pizzeria.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.simulation.DTO.OrderDTO;

@Component
public class BroadcastSocket {
    private final SimpMessagingTemplate template;

    @Autowired
    public BroadcastSocket(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void broadcast(OrderDTO dto) {
        this.template.convertAndSend("/topic", dto);
    }
}
