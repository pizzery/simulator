import cx from 'classnames';

import {DataCell} from './components';

interface Props {
	name: string;
	imageSource: string;
}

const OrderItem = ({name: orderName, imageSource}: Props) => {
	return (
		<div className='h-16 shrink-0 flex items-center bg-slate-900 rounded-xl'>
			<div className='w-full px-3 flex items-center gap-2'>
				<div className='flex items-center gap-5'>
					<img className='w-14' src={imageSource}/>
					<DataCell data={orderName}/>
				</div>
			</div>
		</div>
	);
};

export default OrderItem;
