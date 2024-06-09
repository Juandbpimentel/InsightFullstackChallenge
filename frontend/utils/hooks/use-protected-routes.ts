'use client'
import { useAuth } from '@/utils/hooks/use-auth'
import { useCallback, useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { usePathname } from 'next/navigation'

export default function UseProtectedRoutes() {
	const { sessionToken, user, signOut, loading } = useAuth()
	const pathname = usePathname()
	const router = useRouter()

	const RedirectForDashboard = useCallback(() => {
		router.push('/dashboard')
	}, [router])

	const RedirectForLogin = useCallback(() => {
		router.push('/login')
	}, [router])

	useEffect(() => {
		if (!loading) {
			if ((sessionToken && !user) || (!sessionToken && user)) {
				signOut()
			}
			if (/\/login|\/register/.test(pathname) && user && sessionToken) {
				RedirectForDashboard()
			} else if ('/' === pathname) {
				if (user && sessionToken) {
					RedirectForDashboard()
				} else {
					RedirectForLogin()
				}
			} else if (!/\/login|\/register/.test(pathname) && !('/' === pathname) && !(user && sessionToken)) {
				RedirectForLogin()
			}
		}
	}, [RedirectForDashboard, RedirectForLogin, loading, pathname, sessionToken, signOut, user])

	return loading
}
