import {
	createBrowserRouter,
	redirect,
	RouterProvider,
} from 'react-router-dom';
import axios from 'axios';
import {isObject} from 'lodash';

import {SystemObserver, Configuration} from 'views';

enum RouterPath {
	SystemObserver = '/system-observer',
	Configuration = '/configuration',
}

const router = createBrowserRouter([
	{
		path: '/',
		loader: async () => {
			const {data: config} = await axios.get(
				`${import.meta.env.VITE_API_URL}/config/`
			);

			return redirect(isObject(config) ? 
				RouterPath.SystemObserver : RouterPath.Configuration
			);
		},
	},
	{
		path: RouterPath.SystemObserver,
		loader: async () => {
			const {data: config} = await axios.get(
				`${import.meta.env.VITE_API_URL}/config/`
			);

			return config;
		},
		element: <SystemObserver/>,
	},
	{
		path: RouterPath.Configuration,
		element: <Configuration/>,
	},
]);

const App = () => {
	return (
		<RouterProvider router={router}/>
	);	
};

export default App;
