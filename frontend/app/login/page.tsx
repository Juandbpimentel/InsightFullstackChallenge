'use client'
import { LoginForm } from '@/components/LoginForm'
import { colors } from '@/styles/colors'
import { useAuth } from '@/utils/hooks/use-auth'
import { api } from '@/utils/libs/axios/api'
import { Button, Space } from 'antd'
import { useRouter } from 'next/navigation'
import { useCallback } from 'react'
import UseProtectedRoutes from '@/utils/hooks/use-protected-routes'
import LoadingScreen from '@/components/Loading'
import { useError } from '@/utils/hooks/use-error'
import { AppError } from '@/utils/errors/app-error'

export default function LoginPage() {
	const { signIn } = useAuth()
	const { errorModalRecievingError } = useError()
	const router = useRouter()

	const handleLogin = useCallback(
		async (values: any) => {
			const { email, password } = values
			try {
				let loginData = await api.post('/auth/login', {
					email,
					password,
				})
				await signIn(loginData.data.user, loginData.data.token)
				router.push('/dashboard')
			} catch (error) {
				errorModalRecievingError('Erro ao fazer login', error)
				console.error(error)
			}
		},
		[signIn, router, errorModalRecievingError]
	)

	const loading = UseProtectedRoutes()
	return (
		<>
			{loading ? (
				<LoadingScreen></LoadingScreen>
			) : (
				<main className='flex flex-row justify-center items-center'>
					<div
						className='flex mt-[10vh] mb-[10vh] md:flex-row flex-col h-[30rem] w-[20rem] md:w-[40rem] lg:w-[50rem] xl:w-[60rem] bg-primary-white filter-[drop-shadow]'
						style={{
							borderRadius: '2rem',
							filter: `drop-shadow( 10px 15px 10px ${colors.gray['secondary-shadow']} )`,
							transition: 'all 0.5s ease-in-out',
						}}
					>
						<div
							className='w-[40%]'
							style={{
								background: `linear-gradient(to top left, ${colors['primary-blue']} 45% ,${colors.blue['secondary-dark']} 95%)`,
								borderTopLeftRadius: '2rem',
								borderBottomLeftRadius: '2rem',
							}}
						></div>
						<Space direction='vertical' className='flex p-10 md:w-[60%] w-full' style={{ display: 'flex' }}>
							<h1 className='font-bold'>Login</h1>
							<LoginForm handleLogin={handleLogin}></LoginForm>
						</Space>
					</div>
				</main>
			)}
		</>
	)
}
