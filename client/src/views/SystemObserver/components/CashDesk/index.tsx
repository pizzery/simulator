import {Stat} from './components';

interface Props {
	number: number;
	openOrderCount: number;
	closedOrderCount: number;
}

const CashDesk = ({
	number: cashDeskNumber, openOrderCount, closedOrderCount,
}: Props) => {
	return (
		<div className='
			w-full p-2 flex items-center justify-between rounded-xl bg-slate-800 
			shadow-xl text-slate-300
		'>
			<h3 className='w-full ml-3 text-xl font-bold'>
				no. <span className='text-slate-100'>{cashDeskNumber}</span>
			</h3>
			<div className='flex gap-2'>
				<Stat title='open' value={openOrderCount}/>
				<Stat title='closed' value={closedOrderCount}/>
			</div>
		</div>
	);
};

export default CashDesk;
