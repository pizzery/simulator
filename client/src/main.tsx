import React from 'react';
import {createRoot} from 'react-dom/client';

import App from 'App';

import './index.css';

const containerElement = document.createElement('root');
createRoot(containerElement).render(
	<React.StrictMode>
		<App/>
	</React.StrictMode>
);
document.body.appendChild(containerElement);
