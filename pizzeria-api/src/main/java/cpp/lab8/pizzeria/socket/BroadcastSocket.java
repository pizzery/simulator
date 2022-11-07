package cpp.lab8.pizzeria.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BroadcastSocket {
    private final SimpMessagingTemplate template;

    @Autowired
    public BroadcastSocket(SimpMessagingTemplate template) {
        this.template = template;
    }

    private void broadcast(String path, Object entity) {
        this.template.convertAndSend(path, entity);
    }

    public void broadcastNew(String entityName, Object entity) {
        broadcast("/topic/" + entityName + "/new", entity);
    }

    public void broadcastUpdate(String entityName, Object entity) {
        broadcast("/topic/" + entityName + "/update", entity);
    }
}
