'use client'
import LoadingScreen from '@/components/Loading'
import { useRouter } from 'next/navigation'
import React, { useEffect } from 'react'

export default function Dashboard() {
	const router = useRouter()
	useEffect(() => {
		router.push('/dashboard/suppliers')
	})
	return (
		<>
			<LoadingScreen></LoadingScreen>
		</>
	)
}
