package cpp.lab8.pizzeria.controller;

import cpp.lab8.pizzeria.simulation.queue.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cpp.lab8.pizzeria.simulation.PizzeriaManager;

import java.util.List;

/**
 * Main controller class
 * @apiNote Handles most general for pizzeria operations
 */
@RestController
@RequestMapping("/pizzeria")
public class PizzeriaController {
    private final PizzeriaManager manager;

    @Autowired
    public PizzeriaController(PizzeriaManager manager) {
        this.manager = manager;
    }

    @GetMapping("/start")
    public void start() {
        // TODO: perform checks before starting
        manager.start();
    }

    @PostMapping("/order") 
    public void createOrder(int customerId) {
        manager.createOrderForCustomer(customerId);
    }

    @GetMapping("/queues")
    public ResponseEntity<List<Queue>> getAllQueues() {
        return new ResponseEntity<>(manager.getAllQueues(), HttpStatus.OK);
    }
}
