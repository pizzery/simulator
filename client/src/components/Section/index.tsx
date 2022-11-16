interface Props {
	children: React.ReactNode;
	title: string;
}

const Section = ({children, title}: Props) => {
	return (
		<div className='h-full p-2 flex flex-col bg-slate-600 rounded-2xl'>
			<h2 className='
				mx-auto mb-2 text-2xl text-slate-300 font-light tracking-wide
			'>
				{title}
			</h2>
			<div className='w-full h-0.5 mb-4 bg-slate-500'/>
			<div className='flex flex-col gap-2'>
				{children}
			</div>
		</div>
	);
};

export default Section;
