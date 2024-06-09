import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import React from 'react'
import { AntdRegistry } from '@ant-design/nextjs-registry'
import './globals.css'
import { AppProviders } from '@/utils/providers/app-providers'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
	title: 'Fornecedores App - Aplicação de Desafio da Insight',
	description: 'Essa aplicação é um desafio da Insight para a vaga de bolsista desenvolvedor fullstack',
}

export default function RootLayout({
	children,
}: Readonly<{
	children: React.ReactNode
}>) {
	return (
		<html lang='en'>
			<body className={inter.className}>
				<AppProviders>
					<AntdRegistry>{children}</AntdRegistry>
				</AppProviders>
			</body>
		</html>
	)
}
