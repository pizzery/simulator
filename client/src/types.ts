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

interface PizzaDto {
	pizzaId: number;
	cookId: number;
	state: keyof typeof PizzaState;
}

interface OrderDto {
	orderId: number;
	customerId: number;
	queueId: number;
	state: keyof typeof OrderState,
	pizzas: PizzaDto[];
}

export type {Pizza, Order, PizzaDto, OrderDto};
export {OrderState, PizzaState};
