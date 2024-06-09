'use client'

import { USER_KEY } from './storage-config'

async function storeUser(token: string) {
	localStorage.setItem(USER_KEY, token)
}

async function retrieveUser() {
	return localStorage.getItem(USER_KEY)
}

async function deleteUser() {
	localStorage.removeItem(USER_KEY)
}

export const StorageUser = {
	storeUser,
	retrieveUser,
	deleteUser,
}
