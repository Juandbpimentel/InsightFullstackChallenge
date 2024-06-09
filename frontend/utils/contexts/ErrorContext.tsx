'use client'
import React, { createContext } from 'react'
import { Modal } from 'antd'
import { AppError } from '../errors/app-error'
import { APPLICATION_ERRORCODES } from '../constants/strings'

interface ErrorContextData {
	errorModal: (title: string, content: string) => void
	errorModalRecievingError: (title: string, error: AppError | Error | any) => void
}

export const ErrorContext = createContext<ErrorContextData>({} as ErrorContextData)

export function ErrorContextProvider({ children }: { children: React.ReactNode }) {
	const errorModal = (title: string, content: string): void => {
		Modal.error({
			title: title,
			content: content,
		})
	}

	const errorModalRecievingError = (title: string, error: AppError | Error | any): void => {
		if (error instanceof AppError) {
			console.log('entrou aqui AppError', error)
			if (error.errorcode === undefined || error.errorcode === null) {
				return errorModal(title, error.message)
			} else {
				console.log('entrou aqui AppError errorcode 2', error.errorcode)
				if (error.errorcode === APPLICATION_ERRORCODES.ADMIN_ALREADY_EXISTS) {
					return errorModal(title, 'Administrador já existe')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.INVALID_CREDENTIALS) {
					return errorModal(title, 'Credenciais inválidas')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.FORBIDDEN) {
					return errorModal(title, 'Acesso negado')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.INTERN_ERROR) {
					return errorModal(title, 'A aplicação encontrou um erro interno, tente novamente mais tarde')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.INVALID_ARGUMENTS) {
					return errorModal(title, 'Argumentos inválidos')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.MISSING_BODY_BAD_REQUEST) {
					return errorModal(title, 'Corpo da requisição não encontrado')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.NO_FIELDS_UPDATE) {
					return errorModal(title, 'Nenhum campo foi atualizado')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.SUPPLIER_ALREADY_EXISTS) {
					return errorModal(title, 'Fornecedor já existe')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.SUPPLIER_NOT_FOUND) {
					return errorModal(title, 'Fornecedor não encontrado')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.SUPPLY_NOT_FOUND) {
					return errorModal(title, 'Produto não encontrado')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.UNAUTHORIZED) {
					return errorModal(title, 'Não autorizado')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.USER_ALREADY_EXISTS) {
					return errorModal(title, 'Usuário já existe')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.USER_NOT_FOUND) {
					return errorModal(title, 'Usuário não encontrado')
				}
				if (error.errorcode === APPLICATION_ERRORCODES.VALIDATION_UNEXPECTED_TYPE) {
					return errorModal(title, 'Tipo inesperado')
				} else {
					return errorModal(title, 'Erro desconhecido')
				}
			}
		} else if (error instanceof Error) {
			console.log('entrou aqui Error', error)
			return errorModal(title, error.message)
		} else {
			console.log('entrou aqui Erro desconhecido', error)
			return errorModal(title, 'Erro desconhecido')
		}
	}
	return <ErrorContext.Provider value={{ errorModal, errorModalRecievingError }}>{children}</ErrorContext.Provider>
}
