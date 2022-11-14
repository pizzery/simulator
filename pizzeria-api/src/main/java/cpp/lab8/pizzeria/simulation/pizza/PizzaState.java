package cpp.lab8.pizzeria.simulation.pizza;

/**
 * States for each individual pizza
 */
public enum PizzaState {
    Idle,
	DoughMaking,
	Filling,
	Baking,
	Done;

	public static PizzaState next(PizzaState state) {
		switch(state) {
			case Idle: return DoughMaking;
			case DoughMaking: return Filling;
			case Filling: return Baking;
			default: return Done;
		}
	}
}
