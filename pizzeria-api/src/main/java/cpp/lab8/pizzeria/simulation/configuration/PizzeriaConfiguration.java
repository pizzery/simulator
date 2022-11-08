package cpp.lab8.pizzeria.simulation.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Pizzeria configuration
 */
@Data
@AllArgsConstructor
public class PizzeriaConfiguration {
    // amount of different items available in the menu
    private int menuItems;
    // amount of cash registers
    @Getter
    private int cashRegisters;
    // amount of cooks
    @Getter
    private int cooks;
    // least cooking time in seconds
    private int minCookingTime;
    // timout for new visitor to come
    private int visitorsTimeout;
}
