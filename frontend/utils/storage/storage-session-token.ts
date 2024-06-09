'use client'

import { SESSION_TOKEN_KEY } from './storage-config'

async function storeSessionToken(token: string) {
	localStorage.setItem(SESSION_TOKEN_KEY, token)
}

async function retrieveSessionToken() {
	return localStorage.getItem(SESSION_TOKEN_KEY)
}

async function deleteSessionToken() {
	localStorage.removeItem(SESSION_TOKEN_KEY)
}

export const StorageSessionToken = {
	storeSessionToken,
	retrieveSessionToken,
	deleteSessionToken,
}
