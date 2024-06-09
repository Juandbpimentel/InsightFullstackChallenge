import { UserRole } from '@/utils/types/types'
import { Button, Checkbox, Form, Input, Select } from 'antd'
import Link from 'next/link'
import * as Yup from 'yup'

export function RegisterForm({ handleRegister }: { handleRegister: (values: any) => void }) {
	const formSchema = Yup.object({
		name: Yup.string().required('O nome é obrigatório'),
		email: Yup.string().email('O Endereço de email é inválido').required('O email é obrigatório'),
		password: Yup.string().required('A senha é obrigatória'),
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
		console.error('Register Failed', values)
	}

	return (
		<Form
			onFinish={handleRegister}
			onFinishFailed={handleSubmitFailed}
			autoComplete='on'
			layout='vertical'
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

			<Form.Item<FieldType> label='Nivel de Acesso' name='role' id='role' rules={[yupSync]} style={{ width: '100%' }}>
				<Select>
					<Select.Option value='ADMIN'>Administrador</Select.Option>
					<Select.Option value='USER'>Usuário</Select.Option>
				</Select>
			</Form.Item>

			<Form.Item style={{ width: '100%' }}>
				<div className='flex items-center justify-evenly'>
					<Button type='primary' htmlType='submit'>
						Registrar-se
					</Button>
				</div>
			</Form.Item>
		</Form>
	)
}
