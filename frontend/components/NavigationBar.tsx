'use client'
import { Menu, MenuProps } from 'antd'
import { useRouter } from 'next/navigation'
import { TruckOutlined, UserOutlined } from '@ant-design/icons'
import React, { useState } from 'react'
import Sider from 'antd/es/layout/Sider'

export default function NavigationBar() {
	const [collapsed, setCollapsed] = useState(false)
	const router = useRouter()

	type MenuItem = Required<MenuProps>['items'][number]

	function getItem(label: React.ReactNode, key: React.Key, icon?: React.ReactNode, children?: MenuItem[]): MenuItem {
		return {
			key,
			icon,
			children,
			label,
		} as MenuItem
	}

	const items: MenuItem[] = [
		getItem(<p>Perfil</p>, '2', <UserOutlined />),
		getItem(<p>Fornecedores</p>, '3', <TruckOutlined />),
	]

	const handleMenuClick = (e: any) => {
		if (e.key === '2') router.push('/dashboard/perfil')
		if (e.key === '3') router.push('/dashboard/suppliers')
	}

	return (
		<>
			<Sider collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
				<div className='demo-logo-vertical' />
				<Menu theme='dark' defaultSelectedKeys={['1']} mode='inline' items={items} onClick={handleMenuClick} />
			</Sider>
		</>
	)
}
