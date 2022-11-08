package cpp.lab8.pizzeria.simulation.cook;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.socket.BroadcastSocket;

@Component
public class CookSystem {
    // socket to broadcast modifications
    @Autowired
    private BroadcastSocket socket;

    // list of cooks in the system
    private List<Cook> cooks = new ArrayList<>();

}
