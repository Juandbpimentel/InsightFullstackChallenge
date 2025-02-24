'use client'
import React, { useEffect } from 'react'
import { UserEditForm } from '@/components/UserEditForm'
import { Button, Col, Modal } from 'antd'
import { useRouter } from 'next/navigation'
import { UserRole, UserToken } from '@/utils/types/types'
import { useAuth } from '@/utils/hooks/use-auth'
import { api } from '@/utils/libs/axios/api'
import { useError } from '@/utils/hooks/use-error'
import LoadingScreen from '@/components/Loading'

const Page = () => {
	const { sessionToken, signOut, signIn } = useAuth()
	const { errorModalRecievingError } = useError()
	const [modal, contextHolder] = Modal.useModal()
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

	const handleUpdate = async (values: any) => {
		try {
			let response = await api.put(`/users/me/update`, values, {
				headers: {
					Authorization: `Bearer ${sessionToken}`,
				},
			})
			const { user } = response.data as {
				user: {
					id: string
					name: string
					email: string
					role: UserRole.ADMIN | UserRole.USER
				}
			}
			signIn(user, response.data.token)
			router.push('/dashboard/perfil')
		} catch (error) {
			errorModalRecievingError('Erro ao editar usuário', error)
			console.error(error)
		}
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
		<>
			{contextHolder}
			{loading ? (
				<LoadingScreen></LoadingScreen>
			) : user ? (
				<div>
					<div className='flex flex-column justify-between'>
						<Col>
							<h1 className='font-extrabold text-xl'>Editar Dados Pessoais</h1>
						</Col>
						<Button type='default' onClick={() => router.push('/dashboard/perfil')}>
							Voltar
						</Button>
					</div>
					<UserEditForm user={user} handleUpdate={handleUpdate}></UserEditForm>
				</div>
			) : (
				<>{handleNotFindedUser()}</>
			)}
		</>
	)
}

export default Page
