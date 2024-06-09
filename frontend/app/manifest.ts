import { MetadataRoute } from 'next'

export default function manifest(): MetadataRoute.Manifest {
	return {
		name: 'Fornecedores App - Aplicação de Desafio da Insight',
		short_name: 'Fornecedores App',
		description: 'Essa aplicação é um desafio da Insight para a vaga de bolsista desenvolvedor fullstack',
		start_url: '/',
		display: 'standalone',
		id: '/',
		background_color: '#fff',
		theme_color: '#fff',
		icons: [
			{ src: '/android-chrome-192x192.png', sizes: '192x192', type: 'image/png' },
			{ src: '/android-chrome-512x512.png', sizes: '512x512', type: 'image/png' },
			{
				src: '/favicon.ico',
				sizes: 'any',
				type: 'image/x-icon',
			},
		],
	}
}
