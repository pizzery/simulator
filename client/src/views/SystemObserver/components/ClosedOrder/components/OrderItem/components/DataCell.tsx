interface Props {
	title?: string;
	data: string | number;
	className?: string;
}

const DataCell = ({title, data, className}: Props) => {
	return (
		<div className={`flex flex-col ${className}`}>
			<span className='text-slate-400'>{title}</span>
			<span className='font-bold'>{data}</span>
		</div>
	);
};

export default DataCell;
