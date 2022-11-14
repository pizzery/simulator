package cpp.lab8.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.configuration.PizzeriaConfiguration;

/**
 * Pizzeria configuration controller class
 * @apiNote handles configuration specific methods
 */
@RestController
@RequestMapping("/config")
public class PizzeriaConfigurationController {
    private final PizzeriaManager manager;

    @Autowired
    public PizzeriaConfigurationController(PizzeriaManager manager) {
        this.manager = manager;
    }

    @PostMapping("/")
    public PizzeriaConfiguration setConfiguration(@RequestBody PizzeriaConfiguration config) {
        manager.setConfiguration(config);
        return config;
    }

    @GetMapping("/")
    public PizzeriaConfiguration getConfiguration() {
        return manager.getConfiguration();
    }
}
