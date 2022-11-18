import {useState} from 'react';
import {useLoaderData} from 'react-router-dom';

import {Section, CashDesk, OpenOrder, ClosedOrder} from './components';

import {useOrderSocket} from './hooks';

import {OrderState, type Order, type SystemConfig} from 'types';

import customerNames from 'assets/customerNames.mock.json';

function checkIfOrderIsOpen(order: Order): boolean {
	return order.state !== OrderState.Done;
}

const SystemObserver = () => {
	const systemConfig = useLoaderData() as SystemConfig;	
	const cashDeskNumbers = [...Array(systemConfig.cashRegisters)]
		.map((_, index) => index + 1);

	const [
		openOrderRecord, setOpenOrderRecord,
	] = useState<Record<number, Order>>({});
	const [
		closedOrderRecord, setClosedOrderRecord,
	] = useState<Record<number, Order>>({});

	useOrderSocket(order => {		
		const orderIsOpen = checkIfOrderIsOpen(order);

		if (orderIsOpen) {
			setOpenOrderRecord(prevOrders => {
				return {...prevOrders, [order.id.toString()]: order};
			});
		} else {
			setOpenOrderRecord(prevOpenOrderRecord => {
				if (order.id in prevOpenOrderRecord) {					
					const {
						[order.id]: _, 
						...updatedOpenOrderRecord
					} = prevOpenOrderRecord;
					
					return updatedOpenOrderRecord;
				}

				return prevOpenOrderRecord;
			});

			setClosedOrderRecord(prevOrders => {
				return {...prevOrders, [order.id]: order};
			});
		}
	});

	const renderedOpenOrders = Object.values(openOrderRecord).map(order => {		
		return (
			<OpenOrder
				key={order.id}
				number={order.id}
				cashDeskNumber={order.cashDeskId}
				customerName={customerNames[order.customerId % customerNames.length]}
				pizzas={order.pizzas}
			/>
		);
	});

	const renderedClosedOrders = Object.values(closedOrderRecord)
		.reverse()
		.map(order => {
			return (
				<ClosedOrder
					key={order.id}
					number={order.id}
					cashDeskNumber={order.cashDeskId}
					customerName={customerNames[order.customerId % customerNames.length]}
					pizzas={order.pizzas}
				/>
			);
		});

	const renderedCashDesks = cashDeskNumbers.map(cashDeskNumber => {
		function getCashDeskOrderCount(orderRecord: Record<number, Order>) {
			return Object.values(orderRecord).filter(order => {
				return order.cashDeskId === cashDeskNumber;
			}).length;
		} 

		const openOrderCount = getCashDeskOrderCount(openOrderRecord);
		const closedOrderCount = getCashDeskOrderCount(closedOrderRecord);

		return (
			<CashDesk 
				key={cashDeskNumber}
				number={cashDeskNumber} 
				openOrderCount={openOrderCount}
				closedOrderCount={closedOrderCount}
			/>
		);
	});

	return (
		<div className='
			w-full h-full p-5 bg-slate-800 grid gap-5
			grid-cols-[1fr_minmax(40rem,_1fr)_1fr]
		'>
			<Section title='cash desks'>
				{renderedCashDesks}
			</Section>
			<Section title='open orders'>
				{renderedOpenOrders}
			</Section>
			<Section title='closed orders'>
				{renderedClosedOrders}
			</Section>
		</div>
	);
};

export default SystemObserver;
