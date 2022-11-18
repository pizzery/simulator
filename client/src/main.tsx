import React from 'react';
import {createRoot} from 'react-dom/client';
import {
	QueryClient,
	QueryClientProvider,
} from '@tanstack/react-query';

import App from 'App';

import './index.css';

const queryClient = new QueryClient();

const containerElement = document.createElement('root');

createRoot(containerElement).render(
	<React.StrictMode>
		<QueryClientProvider client={queryClient}>
			<App/>
		</QueryClientProvider>
	</React.StrictMode>
);
document.body.appendChild(containerElement);
