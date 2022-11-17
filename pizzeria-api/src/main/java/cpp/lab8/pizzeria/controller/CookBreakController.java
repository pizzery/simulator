package cpp.lab8.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cpp.lab8.pizzeria.simulation.PizzeriaManager;

@RestController
@RequestMapping("/cook")
public class CookBreakController {
    PizzeriaManager pizzeriaManager;

    @Autowired
    public CookBreakController(@Lazy PizzeriaManager pizzeriaManager) {
        this.pizzeriaManager = pizzeriaManager;
    }

    private boolean checkid(int id) {
        return id > 0 && id <= pizzeriaManager.getConfiguration().getCooks();
    }    

    @GetMapping("/resume")
    public void resumeCook(int id) {
        if(checkid(id)){
            pizzeriaManager.resumeCookingFor(id);
        }
    }

    @GetMapping("/suspend")
    public void suspendCook(int id) {
        if(checkid(id)){
            pizzeriaManager.suspendCookingFor(id);
        }
    }
}
