package cpp.lab8.pizzeria.controller;

import cpp.lab8.pizzeria.simulation.logger.LogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        manager.start();
    }

    @GetMapping("/stop")
    public void stop() {
        manager.stop();
    }

    @GetMapping("/logs")
    public ResponseEntity<List<LogRecord>> getLogs() {
       return  ResponseEntity.ok(manager.getAllRecords());
    }
}
