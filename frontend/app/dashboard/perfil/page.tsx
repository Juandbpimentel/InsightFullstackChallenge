'use client'
import LoadingScreen from '@/components/Loading'
import { AppError } from '@/utils/errors/app-error'
import { useAuth } from '@/utils/hooks/use-auth'
import { useError } from '@/utils/hooks/use-error'
import { api } from '@/utils/libs/axios/api'
import { UserToken } from '@/utils/types/types'
import { Button, Col, Modal, Row, Tag } from 'antd'
import { useRouter } from 'next/navigation'
import React, { useEffect } from 'react'

export default function UserPage() {
	const { sessionToken, signOut } = useAuth()
	const { errorModalRecievingError } = useError()
	const [ modal, contextHolder ] = Modal.useModal()
	const router = useRouter()

	const [user, setUser] = React.useState<UserToken | null>(null)
	const [loading = true, setLoading] = React.useState<boolean>()

	useEffect(() => {
		const fetchMe = async () => {
			try {
				const response = await api.get(`/users/me`, {
					headers: {
						Authorization: `Bearer ${sessionToken}`,
					},
				})
				if (response.data == user)
					return
				setUser(response.data)
			} catch (error) {
				errorModalRecievingError('Erro ao buscar usuário', error)
				console.error(error)
				setUser(null)
			}
			setLoading(false)
		}
		fetchMe().then(r =>
			{
				console.log(r)
			}
		)
	}, [sessionToken])

	const handleDeleteMyUser = async () => {
		modal.confirm({
			title: 'Deseja realmente excluir seu usuário?',
			content: 'Esta ação não poderá ser desfeita',
			onOk: async () => {
				try {
					await api.delete(`/users/me/delete`, {
						headers: {
							Authorization: `Bearer ${sessionToken}`,
						},
					})
					signOut()
					router.push('/')
				} catch (error) {
					errorModalRecievingError('Erro ao deletar usuário', error)
					console.error(error)
				}
			},
		})
	}

	const handleNotFindedUser = () => {
		modal.error({
			title: 'Usuário não encontrado',
			content: 'Você será redirecionado para a página inicial',
			onOk: () => {
				signOut()
				router.push('/')
			},
		})
	}

	return (
		<div>
			{contextHolder}
			<div className='flex flex-column justify-between'>
				<Col>
					<h1 className='font-extrabold text-xl'>Bem vindo ao seu perfil</h1>
				</Col>
				<Button type='default' onClick={() => router.push('/dashboard/suppliers')}>
					Voltar
				</Button>
			</div>
			{loading ? (
				<LoadingScreen></LoadingScreen>
			) : (
				<>
					{user ? (
						<>
							<Row>
								<Col span={12}>
									<p className='font-bold'>Nome</p>
								</Col>
								<Col span={12}>
									<p className='font-bold'>Email</p>
								</Col>
							</Row>
							<Row className='mb-5'>
								<Col span={12}>
									<p>{user.name}</p>
								</Col>
								<Col span={12}>
									<p>{user.email}</p>
								</Col>
							</Row>
							<Row>
								<Col span={12}>
									<p className='font-bold'>Tipo de Acesso no Sistema</p>
								</Col>
							</Row>
							<Row className='mb-5'>
								<Col span={12}>
									<p>
										{
											<Tag color={user.role === 'ADMIN' ? 'red' : 'blue'}>
												{user.role === 'ADMIN' ? 'Administrador' : 'Usuário'}
											</Tag>
										}
									</p>
								</Col>
							</Row>
							<Row className='mt-10'>
								<Col span={3}>
									<Button
										type='default'
										onClick={() => {
											signOut()
											router.push('/')
										}}
									>
										Fazer Logout
									</Button>
								</Col>
								<Col span={3}>
									<Button type='primary' onClick={() => router.push('/dashboard/perfil/edit')}>
										Editar Perfil
									</Button>
								</Col>
								<Col span={3}>
									<Button type='primary' danger onClick={() => handleDeleteMyUser()}>
										Excluir
									</Button>
								</Col>
							</Row>
						</>
					) : (
						<>{handleNotFindedUser()}</>
					)}
				</>
			)}
		</div>
	)
}
