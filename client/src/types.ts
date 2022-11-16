enum PizzaState {
	Idle,
	DoughMaking,
	Filling,
	Baking,
	Done,
}

interface Pizza {
	id: number;
	state: PizzaState;
	cookId: number;
}

enum OrderState {
	Idle,
	InProgress,
	Done,
}

interface Order {
	id: number;
	customerId: number;
	cashDeskId: number;
	pizzas: Pizza[],
	state: OrderState,
}

export type {Pizza, Order};
export {OrderState, PizzaState};
