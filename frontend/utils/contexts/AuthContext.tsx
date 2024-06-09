'use client'
import React, { useCallback, useEffect, createContext } from 'react'
import { StorageUser } from '../storage/storage-user'
import { StorageSessionToken } from '../storage/storage-session-token'
import { UserToken } from '../types/types'
import { useError } from '../hooks/use-error'
import { AppError } from '../errors/app-error'

interface AuthContextData {
	user: UserToken | null
	sessionToken: string | null
	signIn: (user: UserToken, token: string) => Promise<void>
	signOut: () => Promise<void>
	loading: boolean
}

interface Provider {
	children: React.ReactNode
}

export const AuthContext = createContext<AuthContextData>({} as AuthContextData)

export function AuthContextProvider({ children }: Provider) {
	const [user, setUser] = React.useState<UserToken | null>(null)
	const { errorModalRecievingError } = useError()
	const [sessionToken, setSessionToken] = React.useState<string | null>(null)
	const [loading, setLoading] = React.useState(true)

	const saveSessionToken = useCallback(
		async (token: string) => {
			try {
				await StorageSessionToken.storeSessionToken(token)
				setSessionToken(token)
			} catch (error) {
				errorModalRecievingError('Erro ao salvar token', error)
				console.error(error)
			}
		},
		[errorModalRecievingError]
	)

	const saveUser = useCallback(
		async (user: UserToken) => {
			try {
				await StorageUser.storeUser(JSON.stringify(user))
				setUser(user)
			} catch (error) {
				errorModalRecievingError('Erro ao recuperar dados', error)
				console.error(error)
			}
		},
		[errorModalRecievingError]
	)

	const retrieveStorage = useCallback(async () => {
		try {
			const token = await StorageSessionToken.retrieveSessionToken()
			const user = await StorageUser.retrieveUser()

			if (token && user) {
				setSessionToken(token)
				setUser(JSON.parse(user))
			}
			setLoading(false)
		} catch (error) {
			errorModalRecievingError('Erro ao recuperar dados', error)
			console.error(error)
			setSessionToken(null)
			setUser(null)
			setLoading(false)
		}
	}, [errorModalRecievingError])

	const signIn = useCallback(
		async (user: UserToken, token: string) => {
			try {
				await saveSessionToken(token)
				await saveUser(user)
			} catch (error) {
				errorModalRecievingError('Erro ao salvar dados', error)
				console.error(error)
			}
		},
		[errorModalRecievingError, saveSessionToken, saveUser]
	)

	const signOut = useCallback(async () => {
		try {
			await StorageSessionToken.deleteSessionToken()
			await StorageUser.deleteUser()
			setSessionToken(null)
			setUser(null)
		} catch (error) {
			errorModalRecievingError('Erro ao deslogar usuário', error)
			console.error(error)
		}
	}, [errorModalRecievingError])

	useEffect(() => {
		retrieveStorage()
	}, [retrieveStorage])

	return (
		<AuthContext.Provider value={{ user, sessionToken, signIn, signOut, loading }}>{children}</AuthContext.Provider>
	)
}
