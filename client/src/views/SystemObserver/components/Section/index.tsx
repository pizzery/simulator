interface Props {
	children: React.ReactNode;
	title: string;
	className?: string;
}

const Section = ({children, title, className}: Props) => {
	return (
		<div className={`
			relative h-full max-h-full overflow-auto flex flex-col bg-slate-600 
			rounded-2xl ${className}	
		`}>
			<div className='
				sticky z-10 top-0 pt-2 w-full flex align-center backdrop-blur-2xl
				bg-slate-600 bg-opacity-30
			'>
				<h2 className='
					top-0 mx-auto mb-2 text-2xl text-slate-300 font-light 
					tracking-wide
				'>
					{title}
				</h2>
				<div className='absolute bottom-0 w-full h-0.5 bg-slate-500'/>
			</div>
			<div className='flex flex-col gap-2 p-2'>
				{children}
			</div>
		</div>
	);
};

export default Section;
