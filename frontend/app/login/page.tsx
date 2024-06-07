import { colors } from '@/styles/colors'
import { Space } from 'antd'

export default function LoginPage() {
	return (
		<main className='flex h-screen w-screen flex-row justify-center items-center'>
			<div
				className='flex h-[50rem] w-[20rem] md:w-[35rem] lg:w-[80rem] bg-primary-white filter-[drop-shadow]'
				style={{
					borderRadius: '2rem',
					filter: `drop-shadow( 10px 15px 10px ${colors.gray['secondary-shadow']} )`,
					transition: 'all 0.5s ease-in-out',
				}}>
				<div
					className='w-7/12'
					style={{
						background: `linear-gradient(to top left, ${colors['primary-blue']} 45% ,${colors.blue['secondary-dark']} 95%)`,
						borderTopLeftRadius: '2rem',
						borderBottomLeftRadius: '2rem',
					}}></div>
				<Space direction='vertical' className='flex p-12'>
					<h1 className='font-bold'>Teste</h1>
					<h2>teste 2</h2>
				</Space>
			</div>
		</main>
	)
}
