'use client'
import LoadingScreen from '@/components/Loading'
import { EditSupplierForm } from '@/components/supplier/EditSupplierForm'
import { AppError } from '@/utils/errors/app-error'
import { useAuth } from '@/utils/hooks/use-auth'
import { useError } from '@/utils/hooks/use-error'
import { api } from '@/utils/libs/axios/api'
import { Supplier } from '@/utils/types/types'
import { Button, Modal } from 'antd'
import { useRouter } from 'next/navigation'
import React, { useEffect } from 'react'

export default function EditSupplierPage({ params }: { params: { id: string } }) {
	const { errorModalRecievingError } = useError()
	const [modal, contextHolder] = Modal.useModal()
	const { sessionToken, signOut } = useAuth()
	const router = useRouter()
	const id = params.id

	const [supplier, setSupplier] = React.useState<Supplier | null>(null)
	const [loading = true, setLoading] = React.useState<boolean>()

	useEffect(() => {
		const fetchSupplier = async () => {
			try {
				const response = await api.get(`/suppliers/${id}`, {
					headers: {
						Authorization: `Bearer ${sessionToken}`,
					},
				})
				setSupplier(response.data)
			} catch (error) {
				errorModalRecievingError('Erro ao buscar fornecedor', error)
				console.error(error)
				setSupplier(null)
			}
			setLoading(false)
		}
		fetchSupplier()
	}, [errorModalRecievingError, id, sessionToken])

	const handleEditSupplier = async (values: any) => {
		try {
			let response = await api.put(`/suppliers/update/${id}`, values, {
				data: values,
				headers: {
					Authorization: `Bearer ${sessionToken}`,
				},
			})
			router.push('/dashboard/suppliers')
		} catch (error) {
			errorModalRecievingError('Erro ao editar fornecedor', error)
			console.error(error)
		}
	}

	const handleNotFindedSupplier = () => {
		modal.error({
			title: 'Fornecedor não encontrado',
			content: 'Você será redirecionado para a página de fornecedores',
			onOk: () => {
				router.push('/dashboard/suppliers')
			},
		})
	}

	return (
		<div>
			{contextHolder}
			<div className='flex flex-row justify-between'>
				<h1 className='font-extrabold text-xl'>Editar Fornecedor {supplier?.name}</h1>
				<Button type='default' onClick={() => router.push('/dashboard/suppliers')}>
					Voltar
				</Button>
			</div>
			{loading && <LoadingScreen></LoadingScreen>}
			{!loading && (
				<>
					{!supplier ? (
						<>{handleNotFindedSupplier()}</>
					) : (
						<EditSupplierForm handleSubmit={handleEditSupplier} supplier={supplier}></EditSupplierForm>
					)}
				</>
			)}
		</div>
	)
}
