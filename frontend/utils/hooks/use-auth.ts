import { AuthContext } from '@/utils/contexts/AuthContext'

import { useContext } from 'react'

export function useAuth() {
	const context = useContext(AuthContext)
	return context
}
