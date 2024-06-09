'use client'
import { CreateSupplierForm } from '@/components/supplier/CreateSupplierForm'
import { AppError } from '@/utils/errors/app-error'
import { useAuth } from '@/utils/hooks/use-auth'
import { useError } from '@/utils/hooks/use-error'
import { api } from '@/utils/libs/axios/api'
import { Button } from 'antd'
import { useRouter } from 'next/navigation'
import React from 'react'

const CreateSupplierPage: React.FC = () => {
	const { errorModalRecievingError } = useError()
	const { sessionToken } = useAuth()
	const router = useRouter()
	return (
		<div>
			<div className='flex flex-row justify-between'>
				<h1 className='font-extrabold text-xl'>Adicionar Novo Fornecedor</h1>
				<Button type='default' onClick={() => router.push('/dashboard/suppliers')}>
					Voltar
				</Button>
			</div>
			<CreateSupplierForm
				handleSubmit={async (values) => {
					try {
						let response = await api.post('/suppliers/create', values, {
							headers: {
								Authorization: `Bearer ${sessionToken}`,
							},
						})
						router.push('/dashboard/suppliers')
					} catch (error) {
						errorModalRecievingError('Erro ao criar fornecedor', error)
						console.error(error)
					}
				}}
			></CreateSupplierForm>
		</div>
	)
}

export default CreateSupplierPage
