import React from 'react'
import { AuthContextProvider } from '../contexts/AuthContext'
import { ErrorContextProvider } from '../contexts/ErrorContext'

export function AppProviders({ children }: React.PropsWithChildren<{}>) {
	return (
		<AuthContextProvider>
			<ErrorContextProvider>{children}</ErrorContextProvider>
		</AuthContextProvider>
	)
}
