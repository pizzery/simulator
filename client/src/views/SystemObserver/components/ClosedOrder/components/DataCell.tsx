interface Props {
	title: string;
	data: string | number;
}

const DataCell = ({title, data}: Props) => {
	return (
		<div className='flex flex-col flex-1'>
			<span className='text-slate-400'>{title}</span>
			<span className='font-bold'>{data}</span>
		</div>
	);
};

export default DataCell;
