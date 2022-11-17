import {useEffect} from 'react';
import {Client} from '@stomp/stompjs';

import {Section, CashDesk, Order} from 'components';
import {PizzaState} from 'types';

const SOCKET_URL = 'ws://localhost:8080/pizzeria-websocket';

const App = () => {
	useEffect(() => {
		const onConnect = () => {
			client.subscribe('/topic', (message: any) => {
				if (message.body) {
					const messageBody = JSON.parse(message.body);
					console.log(messageBody);
				}
			});
		};

		const client = new Client({
			brokerURL: SOCKET_URL,
			reconnectDelay: 5000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000,
			onConnect,
		});

		client.activate();
	});

	return (
		<>
			<div className='
				w-full h-full p-5 bg-slate-800 grid gap-5
				grid-cols-[1fr_minmax(37rem,_1fr)_1fr]
			'>
				<Section title='cash desks'>
					<CashDesk 
						number={1} 
						openOrderCount={5}
						closedOrderCount={7}
					/>
					<CashDesk 
						number={4} 
						openOrderCount={11}
						closedOrderCount={3}
					/>
				</Section>
				<Section title='open orders'>
					<Order
						number={7}
						cashDeskNumber={1}
						customerName={'Jane Brown'}
						pizzas={[
							{id: 2, cookId: 1, state: PizzaState.Baking},
							{id: 3, cookId: 1, state: PizzaState.Done},
						]}
					/>
				</Section>
				<Section title='closed orders'>
				</Section>
			</div>
		</>
	);
};

export default App;
