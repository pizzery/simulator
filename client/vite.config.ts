import {defineConfig} from 'vite';
import react from '@vitejs/plugin-react';
import tsconfigPaths from 'vite-tsconfig-paths';
import postcss from './postcss.config';

export default defineConfig({
	plugins: [react(), tsconfigPaths()],
	css: {
		postcss,
	},
});
