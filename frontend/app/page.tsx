'use client'
import LoadingScreen from '@/components/Loading'
import UseProtectedRoutes from '@/utils/hooks/use-protected-routes'

export default function RootPage() {
	UseProtectedRoutes()
	return <LoadingScreen></LoadingScreen>
}
