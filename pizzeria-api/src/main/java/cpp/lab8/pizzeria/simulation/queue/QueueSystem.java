package cpp.lab8.pizzeria.simulation.queue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.socket.BroadcastSocket;

@Component
public class QueueSystem {
    // socket to broadcast modifications
    @Autowired
    private BroadcastSocket socket;

    // list of queues in the system
    private List<Queue> queues = new ArrayList<>();

}
