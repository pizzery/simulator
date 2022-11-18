import {DataCell, OrderItem} from './components';
import {Divider} from 'components';

import {Pizza} from 'types';

import menuRecord from 'assets/menuRecord.json';

interface Props {
	number: number;
	customerName: string;
	cashDeskNumber: number;
	pizzas: Pizza[];
}

function getMenuItemById(id: number) {
	return menuRecord[
		(id % Object.keys(menuRecord).length).toString() as keyof typeof menuRecord
	];
}

const ClosedOrder = ({
	number: orderNumber, customerName, cashDeskNumber, pizzas,
}: Props) => {
	const renderedPizzas = pizzas.map(({id}) => {
		const {name, imageSource} = getMenuItemById(id);

		return (
			<OrderItem 
				key={id}
				name={name}
				imageSource={imageSource}
			/>
		);
	});

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
		</div>
	);
};

export default ClosedOrder;
