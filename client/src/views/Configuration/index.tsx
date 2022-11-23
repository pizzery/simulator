import {useState} from 'react';
import axios from 'axios';
import {useNavigate} from 'react-router-dom';

import {ConfigItem} from './components';

const Configuration = () => {
	const [menuItems, setMenuItems] = useState(5);
	const [cashRegisters, setCashRegisters] = useState(4);
	const [cooks, setCooks] = useState(4);
	const [minCookingTime, setMinCookingTime] = useState(15);
	const [visitorsTimeout, setVisitorsTimeout] = useState(10);
	const [strategy, setStrategy] = useState(3);
	const [cookStrategy, setCookStrategy] = useState(1);

	const navigate = useNavigate();

	return (
		<div className='
			w-full h-full p-5 bg-slate-800 flex flex-col
			items-center text-white justify-center
		'>
			<div className='flex flex-col gap-2 items-stretch'>
				<ConfigItem
					title='Number of menu items'
					value={menuItems}
					onValueChange={setMenuItems}
				/>
				<ConfigItem
					title='Number of cash desks'
					value={cashRegisters}
					onValueChange={setCashRegisters}
				/>
				<ConfigItem
					title='Number of cooks'
					value={cooks}
					onValueChange={setCooks}
				/>
				<ConfigItem
					title='Min cooking time'
					value={minCookingTime}
					onValueChange={setMinCookingTime}
				/>
				<ConfigItem
					title='Visitor timeout'
					value={visitorsTimeout}
					onValueChange={setVisitorsTimeout}
				/>
				<ConfigItem
					title='Strategy number'
					value={strategy}
					onValueChange={setStrategy}
				/>
				<ConfigItem
					title='Cook strategy number'
					value={cookStrategy}
					onValueChange={setCookStrategy}
				/>
				<button
					className='bg-slate-700 rounded-full p-3 font-bold text-lg'
					onClick={() => {
						axios.post(`${import.meta.env.VITE_API_URL}/config/`, {
							menuItems,
							cashRegisters,
							cooks,
							minCookingTime,
							visitorsTimeout,
							strategy,
							cookStrategy,
						});

						navigate('/system-observer');
					}}
				>Start</button>
			</div>
		</div>
	);
};

export default Configuration;
