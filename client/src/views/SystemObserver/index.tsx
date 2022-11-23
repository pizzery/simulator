import {useState, useEffect} from 'react';
import {useLoaderData} from 'react-router-dom';
import axios from 'axios';

import {Section, CashDesk, OpenOrder, ClosedOrder} from './components';

import {useOrderSocket} from './hooks';

import {OrderState, type Order, type SystemConfig} from 'types';

import customerNames from 'assets/customerNames.mock.json';
import cookRecord from 'assets/cookRecord.json';

function checkIfOrderIsOpen(order: Order): boolean {
	return order.state !== OrderState.Done;
}

const SystemObserver = () => {
	const [systemConfig, setSystemConfig] = useState<SystemConfig>({
		menuItems: 0,
		cashRegisters: 0,
		cooks: 0,
		minCookingTime: 0,
		visitorsTimeout: 0,
		strategy: 0,
		cookStrategy: 0,
	});
	
	useEffect(() => {
		async function fetchConfig() {
			const {data: config} = await axios.get(
				`${import.meta.env.VITE_API_URL}/config/`
			);

			setSystemConfig(config);
		}

		setTimeout(() => {
			fetchConfig();
			axios.get(`${import.meta.env.VITE_API_URL}/pizzeria/start`);
		}, 1000);
	}, []);

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

	const cashDeskNumbers = [...Array(systemConfig.cashRegisters)]
		.map((_, index) => index + 1);
	const cookIds = [...Array(systemConfig.cooks)]
		.map((_, index) => index + 1);

	interface Cook {
		id: number;
		name: string;
		isActive: boolean;
	}

	const [cooks, setCooks] = useState<Cook[]>([]);

	useEffect(() => {
		if (!cookIds) return;

		setCooks(cookIds.map(cookId => {
			const cook = cookRecord[cookId.toString() as keyof typeof cookRecord];
	
			return {
				id: cookId,
				name: cook.name,
				isActive: true,
			};
		}));
	}, [JSON.stringify(cookIds)]);

	const [
		openOrderRecord, setOpenOrderRecord,
	] = useState<Record<number, Order>>({});
	const [
		closedOrderRecord, setClosedOrderRecord,
	] = useState<Record<number, Order>>({});

	if (!systemConfig.menuItems ) return null;

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

	const renderedCooks = cooks.map(({id, name, isActive}) => {
		return (
			<div 
				key={id} 
				className='
					w-full p-2 flex items-center justify-between rounded-xl bg-slate-800 
					shadow-xl text-slate-300
				'
			>
				<h3 className='w-full ml-3 text-xl font-bold'>
					{name}
				</h3>
				<div className='flex gap-2'>
					<button
						className='
							h-full px-3 py-1 flex gap-2 items-center rounded-lg bg-slate-300
							text-slate-900 font-bold
						'
						onClick={() => {
							setCooks(prevCooks => {
								const cooksClone = [...prevCooks];
								const cookIndex = prevCooks.findIndex(cook => cook.id === id);
								const cookClone = {...cooksClone[cookIndex]};

								cookClone.isActive 
									? axios.get(`${import.meta.env.VITE_API_URL}/cook/suspend`, {
										params: {id},
									})
									: axios.get(`${import.meta.env.VITE_API_URL}/cook/resume`,  {
										params: {id},
									});

								cookClone.isActive = !cookClone.isActive;
								cooksClone[cookIndex] = cookClone;

								return cooksClone;
							});
						}}
					>{isActive ? 'Suspend' : 'Resume'}</button>
				</div>
			</div>
		);
	});

	return (
		<div className='
			w-full h-full p-5 bg-slate-800 grid gap-5
			grid-cols-[1fr_minmax(40rem,_1fr)_1fr]
		'>
			<div className='grid grid-rows-2 gap-5'>
				<Section title='cash desks'>
					{renderedCashDesks}
				</Section>
				<Section title='cash desks'>
					{renderedCooks}
				</Section>
			</div>
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
