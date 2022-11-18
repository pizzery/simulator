interface Props {
	title: string;
	value: number;
	onValueChange: (value: number) => void;
}

const ConfigItem = ({title, value, onValueChange}: Props) => {
	return (
		<div className='
			flex flex-1 justify-between bg-slate-900 rounded-xl items-center p-5
			text-slate-300 text-lg font-bold
		'>
			<label className='flex flex-1 justify-between items-center gap-10'>
				{title}
				<input 
					className='
						rounded-full h-8 w-36 bg-slate-700 text-slate-100 flex px-5 
						text-center
					'
					type='number' 
					value={value}
					onChange={event => onValueChange(event.target.valueAsNumber)}
				/>
			</label>
		</div>
	);
};

export default ConfigItem;
