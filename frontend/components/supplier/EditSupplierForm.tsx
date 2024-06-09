'use client'
import { Supplier, SupplyType } from '@/utils/types/types'
import { Button, Form, Input, Select, SelectProps } from 'antd'
import { useEffect } from 'react'
import * as Yup from 'yup'

export function EditSupplierForm({
	handleSubmit,
	supplier,
}: {
	handleSubmit: (values: any) => void
	supplier: Supplier
}) {
	const [form] = Form.useForm()

	useEffect(() => {
		form.setFieldsValue({
			name: supplier.name,
			email: supplier.email,
			phone: supplier.phone,
			address: supplier.address,
			supplyTypes: supplier.supplyTypes,
		})
		// eslint-disable-next-line react-hooks/exhaustive-deps
	})
	const formSchema = Yup.object({
		name: Yup.string().required('O nome é obrigatório'),
		phone: Yup.string()
			.required('O telefone é obrigatório')
			.matches(/^\(\d{2}\)\s\d{4,5}\-\d{4}$/, 'O telefone precisa estar no formato (00) 00000-0000'),
		email: Yup.string().email('O Endereço de email é inválido').required('O email é obrigatório'),
		address: Yup.string().required('O endereço é obrigatório'),
		supplyTypes: Yup.array(
			Yup.string<SupplyType>()
				.required('O tipo de fornecimento é obrigatório')
				.matches(
					/(PRODUCTS|SERVICES|RAW_MATERIALS|OTHERS)/,
					'O tipo de fornecimento deve ser Produtos, Serviços, Matéria Primas ou Outros'
				)
		)
			.min(1, 'Selecione pelo menos um tipo de fornecimento')
			.required('O tipo de fornecimento é obrigatório'),
	})

	const yupSync = {
		async validator({ field }: any, value: any) {
			await formSchema.validateSyncAt(field, { [field]: value })
		},
	}

	type FieldType = {
		email?: string
		password?: string
		supplyTypes?: SupplyType[]
		phone?: string
		address?: string
		name?: string
	}

	const handleSubmitFailed = async (values: any) => {
		console.error('Creation Failed', values)
	}

	const handleChange = (value: any) => {}

	const options: SelectProps['options'] = []

	options.push({
		label: 'Fornecedor de produtos',
		value: 'PRODUCTS',
	})
	options.push({
		label: 'Fornecedor de serviços',
		value: 'SERVICES',
	})
	options.push({
		label: 'Fornecedor de matéria prima',
		value: 'RAW_MATERIALS',
	})
	options.push({
		label: 'Outros Tipos de Fornecimento',
		value: 'OTHERS',
	})

	return (
		<div className='mt-10' style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
			<Form
				onFinish={handleSubmit}
				onFinishFailed={handleSubmitFailed}
				onFieldsChange={handleChange}
				initialValues={{
					name: supplier.name,
					email: supplier.email,
					phone: supplier.phone,
					address: supplier.address,
					supplyTypes: supplier.supplyTypes,
				}}
				form={form}
				layout='vertical'
				style={{ width: '60%', alignItems: 'center', display: 'flex', flexDirection: 'column' }}
			>
				<Form.Item<FieldType> label='Nome' name='name' id='name' rules={[yupSync]} style={{ width: '100%' }}>
					<Input name='name' placeholder='Name'></Input>
				</Form.Item>
				<Form.Item<FieldType> label='Email' name='email' id='email' rules={[yupSync]} style={{ width: '100%' }}>
					<Input name='email' placeholder='email@example'></Input>
				</Form.Item>
				<Form.Item<FieldType> label='Telefone' name='phone' id='phone' rules={[yupSync]} style={{ width: '100%' }}>
					<Input name='phone' placeholder='(00) 00000-0000'></Input>
				</Form.Item>

				<Form.Item<FieldType> label='Endereço' name='address' id='address' rules={[yupSync]} style={{ width: '100%' }}>
					<Input name='address' placeholder='Avenida Brasil, 100, Rio de Janeiro, Rio de Janeiro, Brasil'></Input>
				</Form.Item>

				<Form.Item<FieldType>
					label='Tipo de fornecedor'
					name='supplyTypes'
					id='supplyTypes'
					rules={[yupSync]}
					style={{ width: '100%' }}
				>
					<Select
						mode='multiple'
						allowClear
						style={{ width: '100%' }}
						placeholder='Please select'
						onChange={handleChange}
						options={options}
					/>
				</Form.Item>

				<Form.Item style={{ width: '100%' }}>
					<div className='flex items-center justify-evenly'>
						<Button type='primary' htmlType='submit'>
							Atualizar Fornecedor
						</Button>
						{
							//Limpar campos
						}
						<Button type='default' htmlType='reset'>
							Limpar
						</Button>
					</div>
				</Form.Item>
			</Form>
		</div>
	)
}
