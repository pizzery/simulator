interface Props {
	title: string;
	value: number;
}

const Stat = ({title, value}: Props) => {
	return (
		<div className='
			h-full px-3 py-1 flex gap-2 items-center rounded-lg bg-slate-900
		'>
			<span className='font-bold'>{value}</span>
			<span className='text-slate-400'>{title}</span>
		</div>
	);
};

export default Stat;
