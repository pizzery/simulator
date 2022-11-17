import {Section, CashDesk, Order} from './components';

import {useOrderSocket} from './hooks';

import {PizzaState, OrderState} from 'types';

const SystemObserver = () => {
	useOrderSocket(order => {		
	});

	return (
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
	);
};

export default SystemObserver;
