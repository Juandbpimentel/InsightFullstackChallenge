'use client'
import LoadingScreen from '@/components/Loading'
import { AppError } from '@/utils/errors/app-error'
import { useAuth } from '@/utils/hooks/use-auth'
import { useError } from '@/utils/hooks/use-error'
import { api } from '@/utils/libs/axios/api'
import { Supplier, SupplyType } from '@/utils/types/types'
import { Button, Col, Modal, Row, Tag } from 'antd'
import { useRouter } from 'next/navigation'
import React, { useEffect } from 'react'

export default function ViewSupplierPage({ params }: { params: { id: string } }) {
	const { sessionToken, signOut } = useAuth()
	const [modal, contextHolder] = Modal.useModal()
	const { errorModalRecievingError } = useError()
	const router = useRouter()
	const id = params.id

	const [supplier, setSupplier] = React.useState<Supplier | null>(null)
	const [loading = true, setLoading] = React.useState<boolean>()

	const handleNotFindedSupplier = () => {
		modal.error({
			title: 'Fornecedor não encontrado',
			content: 'Você será redirecionado para a página de fornecedores',
			onOk: () => {
				router.push('/dashboard/suppliers')
			},
		})
	}

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
	})

	return (
		<div>
			{contextHolder}
			<div className='flex flex-row justify-between'>
				<h1 className='font-extrabold text-xl'>Dados do Fornecedor {supplier?.name}</h1>
				<Button type='default' onClick={() => router.push('/dashboard/suppliers')}>
					Voltar
				</Button>
			</div>
			{loading ? (
				<LoadingScreen></LoadingScreen>
			) : (
				<>
					{supplier ? (
						<>
							<Row>
								<Col span={12}>
									<p className='font-bold'>Nome</p>
								</Col>
								<Col span={12}>
									<p className='font-bold'>CNPJ</p>
								</Col>
							</Row>
							<Row className='mb-5'>
								<Col span={12}>
									<p>{supplier.name}</p>
								</Col>
								<Col span={12}>
									<p>{supplier.cnpj}</p>
								</Col>
							</Row>
							<Row>
								<Col span={12}>
									<p className='font-bold'>Email</p>
								</Col>
								<Col span={12}>
									<p className='font-bold'>Telefone</p>
								</Col>
							</Row>
							<Row className='mb-5'>
								<Col span={12}>
									<p>{supplier.email}</p>
								</Col>
								<Col span={12}>
									<p>{supplier.phone}</p>
								</Col>
							</Row>
							<Row>
								<Col span={12}>
									<p className='font-bold'>Tipos de Fornecimento</p>
								</Col>
							</Row>
							<Row>
								<Col span={12}>
									{supplier.supplyTypes.map((type, key) => {
										let color =
											type === SupplyType.PRODUCTS
												? 'green'
												: type === SupplyType.SERVICES
												? 'blue'
												: type === SupplyType.RAW_MATERIALS
												? 'red'
												: 'gray'
										let text =
											type === SupplyType.PRODUCTS
												? 'Produtos'
												: type === SupplyType.SERVICES
												? 'Serviços'
												: type === SupplyType.RAW_MATERIALS
												? 'Matéria Prima'
												: 'Outros'
										return (
											<Tag key={key} color={color}>
												{text}
											</Tag>
										)
									})}
								</Col>
							</Row>
						</>
					) : (
						<>{handleNotFindedSupplier()}</>
					)}
				</>
			)}
		</div>
	)
}
