import {useEffect} from 'react';
import {Client, IMessage as Message} from '@stomp/stompjs';

import type {Order, OrderDto} from 'types';
import {OrderState, PizzaState} from 'types';

type OrderMessageCallback = (message: Order) => void;

function orderDtoToOrder(orderDto: OrderDto): Order {
	return {
		id: orderDto.orderId,
		customerId: orderDto.customerId,
		cashDeskId: orderDto.queueId,
		state: OrderState[orderDto.state],
		pizzas: orderDto.pizzas.map(pizzaDto => {
			return {
				id: pizzaDto.pizzaId,
				cookId: pizzaDto.cookId,
				state: PizzaState[pizzaDto.state],
			};
		}),
	};
}

function useOrderSocket(messageCallback: OrderMessageCallback) {
	useEffect(() => {
		const onConnect = () => {
			client.subscribe('/topic', (message: Message) => {			
				if (!message.body) return;
				
				const orderDto: OrderDto = JSON.parse(message.body);
				console.log(orderDto);
				messageCallback(orderDtoToOrder(orderDto));
			});
		};
	
		const client = new Client({
			brokerURL: import.meta.env.VITE_SOCKET_URL,
			onConnect,
		});
	
		client.activate();
	}, []);
}

export default useOrderSocket;
