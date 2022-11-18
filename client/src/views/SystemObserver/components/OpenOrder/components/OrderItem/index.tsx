import cx from 'classnames';

import {DataCell} from './components';
import Divider from 'components/Divider';

import {PizzaState} from 'types';

interface Props {
	name: string;
	imageSource: string;
	state: PizzaState;
	cookName?: string;
}

const states = ['dough', 'topping', 'baking'];

const OrderItem = ({name: orderName, imageSource, state, cookName}: Props) => {
	const renderedStates = states.map((iteratedState, index) => {
		const stateIsPassed = index + PizzaState.Idle + 1 <= state;

		return (
			<div 
				key={iteratedState} 
				className={cx(
					'relative flex flex-1 flex-col justify-center items-center',
					'text-slate-500',
					{'text-slate-200': stateIsPassed}
				)}
			>
				<span>{iteratedState}</span>
				<div className={cx(
					'absolute bottom-0 w-full h-0.5 rounded-full bg-slate-500',
					{'bg-slate-200': stateIsPassed}
				)}/>
			</div>
		);
	});

	return (
		<div className='h-16 shrink-0 flex items-center bg-slate-900 rounded-xl'>
			<div className='w-full px-3 flex items-center gap-2'>
				<div className='w-[58.5%] flex gap-2 items-center justify-between'>
					<div className='flex items-center gap-5'>
						<img className='w-14' src={imageSource}/>
						<DataCell className='w-16' data={orderName}/>
					</div>
					<div className='flex gap-2'>
						<DataCell 
							className='items-end' 
							title='cook' 
							data={cookName || 'not assigned'}
						/>
						<Divider/>
					</div>
				</div>
				<div className='flex flex-1 self-stretch justify-between gap-1'>
					{renderedStates}
				</div>
			</div>
		</div>
	);
};

export default OrderItem;
