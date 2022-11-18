import {DataCell, OrderItem} from './components';
import {Divider} from 'components';

import {Pizza, PizzaState} from 'types';

import menuRecord from 'assets/menuRecord.json';
import cookRecord from 'assets/cookRecord.json';

interface Props {
	number: number;
	customerName: string;
	cashDeskNumber: number;
	pizzas: Pizza[];
}

function getCookNameById(id: number): string | undefined {
	return cookRecord[
		(id % Object.keys(cookRecord).length).toString() as keyof typeof cookRecord
	]?.name;
}

function getMenuItemById(id: number) {
	return menuRecord[
		(id % Object.keys(menuRecord).length).toString() as keyof typeof menuRecord
	];
}

const OpenOrder = ({
	number: orderNumber, customerName, cashDeskNumber, pizzas,
}: Props) => {
	const renderedPizzas = pizzas.map(({id, cookId, state}) => {
		const cookName = getCookNameById(cookId);
		const {name, imageSource} = getMenuItemById(id);

		return (
			<OrderItem 
				key={id}
				name={name}
				imageSource={imageSource}
				state={state}
				cookName={cookName}
			/>
		);
	});

	const closedOrderItems = pizzas.filter(pizza => {
		return pizza.state === PizzaState.Done;
	});

	const progressValue = closedOrderItems.length / pizzas.length;

	return (
		<div className='
			relative w-full p-2 pb-1 flex flex-col items-center justify-between 
			rounded-xl bg-slate-800 shadow-xl text-slate-300
		'>
			<div className='w-full px-3 flex items-center gap-2'>
				<h3 className='w-16 ml-1 text-xl font-bold'>
					no. <span className='text-slate-100'>{orderNumber}</span>
				</h3>
				<Divider/>
				<DataCell
					title='cash desk no.'
					data={cashDeskNumber}
				/>
				<Divider/>
				<DataCell
					title='customer'
					data={customerName}
				/>
			</div>
			<div className='w-full h-0.5 my-2 bg-slate-600'/>
			<div className='
				max-h-24 overflow-auto -mt-2 -mb-1 pt-2 pb-2 flex flex-col 
				self-stretch gap-2
			'>
				{renderedPizzas}
			</div>
			<div className='
				w-full h-2 mt-1 overflow-hidden rounded-full bg-slate-700 flex p-0.5
				shadow-md
			'>
				<div 
					className='h-full bg-slate-400 rounded-full' 
					style={{flex: progressValue}}
				/>
			</div>
		</div>
	);
};

export default OpenOrder;
