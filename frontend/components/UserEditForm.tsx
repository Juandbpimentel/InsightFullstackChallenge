import { UserRole, UserToken } from '@/utils/types/types'
import { Button, Form, Input, Select } from 'antd'
import { useEffect } from 'react'
import * as Yup from 'yup'

export function UserEditForm({ handleUpdate, user }: { handleUpdate: (values: any) => void; user: UserToken }) {
	const [form] = Form.useForm()

	const formSchema = Yup.object({
		name: Yup.string().required('O nome é obrigatório'),
		email: Yup.string().email('O Endereço de email é inválido').required('O email é obrigatório'),
		password: Yup.string(),
		role: Yup.string()
			.required('O nível de acesso é obrigatório')
			.matches(/(ADMIN|USER)/, 'O nível de acesso deve ser Administrador ou Usuário'),
	})

	const yupSync = {
		async validator({ field }: any, value: any) {
			await formSchema.validateSyncAt(field, { [field]: value })
		},
	}

	type FieldType = {
		name?: string
		email?: string
		password?: string
		role?: UserRole
	}

	const handleSubmitFailed = async (values: any) => {
		console.error('Update Failed', values)
	}

	useEffect(() => {
		form.setFieldsValue({
			name: user.name,
			email: user.email,
			role: user.role,
		})
		// eslint-disable-next-line react-hooks/exhaustive-deps
	})

	return (
		<div className='mt-10' style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
			<div className='flex flex-column justify-between' style={{ width: '60%' }}>
				<Form
					onFinish={handleUpdate}
					onFinishFailed={handleSubmitFailed}
					autoComplete='on'
					layout='vertical'
					form={form}
					initialValues={{
						name: user.name,
						email: user.email,
						role: user.role,
					}}
					style={{ width: '100%', alignItems: 'center', display: 'flex', flexDirection: 'column' }}
				>
					<Form.Item<FieldType> label='Nome' name='name' id='name' rules={[yupSync]} style={{ width: '100%' }}>
						<Input name='name' placeholder='Name'></Input>
					</Form.Item>

					<Form.Item<FieldType> label='E-mail' name='email' id='email' rules={[yupSync]} style={{ width: '100%' }}>
						<Input name='email' placeholder='email@example'></Input>
					</Form.Item>

					<Form.Item<FieldType> label='Senha' name='password' id='password' rules={[yupSync]} style={{ width: '100%' }}>
						<Input.Password placeholder='******' />
					</Form.Item>

					<Form.Item<FieldType>
						label='Nivel de Acesso'
						name='role'
						id='role'
						rules={[yupSync]}
						style={{ width: '100%' }}
					>
						<Select>
							<Select.Option value='ADMIN'>Administrador</Select.Option>
							<Select.Option value='USER'>Usuário</Select.Option>
						</Select>
					</Form.Item>

					<Form.Item style={{ width: '100%' }}>
						<div className='flex items-center justify-evenly'>
							<Button type='primary' htmlType='submit'>
								Atualizar Dados
							</Button>
							<Button type='default' htmlType='reset'>
								Limpar
							</Button>
						</div>
					</Form.Item>
				</Form>
			</div>
		</div>
	)
}
