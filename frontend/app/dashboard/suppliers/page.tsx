'use client'
import { Button, Modal } from 'antd'
import { useRouter } from 'next/navigation'
import React, { useCallback, useEffect, useState } from 'react'
import * as Types from '@/utils/types/types'
import SupplierTable from '@/components/supplier/SupplierTable'
import { useAuth } from '@/utils/hooks/use-auth'
import { api } from '@/utils/libs/axios/api'
import { useError } from '@/utils/hooks/use-error'

export default function FornecedoresPage() {
	const { sessionToken } = useAuth()
	const { errorModalRecievingError } = useError()
	const [modal, contextHolder] = Modal.useModal()
	const [suppliers, setSuppliers] = useState<Types.Supplier[]>([])

	const fetchSuppliers = useCallback(async () => {
		try {
			let response = await api.get('/suppliers/list', {
				headers: {
					Authorization: `Bearer ${sessionToken}`,
				},
			})
			const json = await response.data
			setSuppliers(json)
		} catch (error) {
			errorModalRecievingError('Erro ao buscar fornecedores', error)
			console.error(error)
		}
	}, [errorModalRecievingError, sessionToken])

	useEffect(() => {
		try {
			fetchSuppliers()
		} catch (error) {
			errorModalRecievingError('Erro ao buscar fornecedores', error)
			console.error(error)
		}
	})

	const router = useRouter()

	const handleEditSupplier = (id: string) => () => {
		router.push(`/dashboard/suppliers/edit/${id}`)
	}

	const handleDeleteSupplier = (id: string) => async () => {
		modal.confirm({
			title: 'Deseja realmente excluir este fornecedor?',
			content: 'Esta ação não poderá ser desfeita',
			onOk: async () => {
				try {
					let response = await api.delete(`/suppliers/delete/${id}`, {
						headers: {
							Authorization: `Bearer ${sessionToken}`,
						},
					})
					setSuppliers(suppliers.filter((supplier) => supplier.id !== id))
				} catch (error) {
					errorModalRecievingError('Erro ao deletar fornecedor', error)
					console.error(error)
				}
			},
		})
	}

	const handleViewSupplier = (id: string) => () => {
		router.push(`/dashboard/suppliers/${id}`)
	}

	return (
		<div>
			{contextHolder}
			<div className='flex flex-row justify-between'>
				<h1 className='font-extrabold text-xl'>Fornecedores</h1>
				<Button className='mr-4' type='primary' onClick={() => router.push('/dashboard/suppliers/create')}>
					Adicionar Fornecedor
				</Button>
			</div>

			<SupplierTable
				data={suppliers}
				handleEdit={handleEditSupplier}
				handleDelete={handleDeleteSupplier}
				handleView={handleViewSupplier}
			></SupplierTable>
		</div>
	)
}
