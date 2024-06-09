'use client'
import React, { useEffect, useState } from 'react'
import { Breadcrumb, Layout } from 'antd'
import UseProtectedRoutes from '@/utils/hooks/use-protected-routes'
import LoadingScreen from '@/components/Loading'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { Footer, Content } from 'antd/es/layout/layout'
import NavigationBar from '@/components/NavigationBar'

export default function DashboardLayout({
	children,
}: Readonly<{
	children: React.ReactNode
}>) {
	const [pagePath, setPagePath] = useState<string[]>([])
	const rawPath = usePathname()

	useEffect(() => {
		let pathCapitalized = rawPath
			.split('/')
			.filter((path) => path !== '')
			.map((path) => path.charAt(0).toUpperCase() + path.slice(1))
			.map((path) => path.charAt(0).toUpperCase() + path.slice(1))
		setPagePath(pathCapitalized)
	}, [rawPath])

	const loading = UseProtectedRoutes()
	return (
		<>
			{loading ? (
				<LoadingScreen />
			) : (
				<Layout style={{ minHeight: '100vh' }}>
					<NavigationBar />
					<Layout>
						<Content style={{ margin: '0 16px' }}>
							<Breadcrumb style={{ margin: '16px 0' }}>
								{pagePath.map((path, index) => {
									let linkpath = pagePath
										.slice(0, index + 1)
										.join('/')
										.toLowerCase()
									return (
										<Breadcrumb.Item key={index}>
											<Link href={`/${linkpath}`}>
												{path.replace('Suppliers', 'Fornecedores').replace('Users', 'Usuários')}
											</Link>
										</Breadcrumb.Item>
									)
								})}
							</Breadcrumb>
							{children}
						</Content>
						<Footer style={{ textAlign: 'center' }}>Ant Design ©{new Date().getFullYear()} Created by Ant UED</Footer>
					</Layout>
				</Layout>
			)}
		</>
	)
}
