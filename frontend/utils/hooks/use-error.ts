import { ErrorContext } from '@/utils/contexts/ErrorContext'

import { useContext } from 'react'

export function useError() {
	const context = useContext(ErrorContext)
	return context
}
