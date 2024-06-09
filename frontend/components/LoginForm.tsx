import { Button, Checkbox, Form, FormProps, Input } from 'antd'
import { Formik, useFormik } from 'formik'
import Link from 'next/link'
import * as Yup from 'yup'

export function LoginForm({ handleLogin }: { handleLogin: (values: any) => void }) {
	const formSchema = Yup.object({
		email: Yup.string().email('O Endereço de email é inválido').required('O email é obrigatório'),
		password: Yup.string().required('A senha é obrigatória'),
	})

	const yupSync = {
		async validator({ field }: any, value: any) {
			await formSchema.validateSyncAt(field, { [field]: value })
		},
	}

	type FieldType = {
		email?: string
		password?: string
	}

	const handleSubmitFailed = async (values: any) => {
		console.error('Login Failed', values)
	}

	return (
		<Form onFinish={handleLogin} onFinishFailed={handleSubmitFailed} autoComplete='on' layout='vertical' style={{ width: '100%', alignItems: 'center', display: 'flex', flexDirection: 'column' }}>
			<Form.Item<FieldType> label='Email' name='email' id='email' rules={[yupSync]} style={{ width: '100%' }}>
				<Input name='email' placeholder='email@example'></Input>
			</Form.Item>

			<Form.Item<FieldType> label='Password' name='password' id='password' rules={[yupSync]} style={{ width: '100%' }}>
				<Input.Password placeholder='******' />
			</Form.Item>

			<Form.Item style={{ width: '100%' }}>
				<div className='flex items-center justify-evenly'>
					<Button type='primary' htmlType='submit'>
						Fazer Login
					</Button>
					<Link href='/register'>Registrar-se</Link>
				</div>
			</Form.Item>
		</Form>
	)
}
