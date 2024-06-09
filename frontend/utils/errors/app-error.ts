import { APPLICATION_STRINGS } from '@/utils/constants/strings'
import { AxiosError } from 'axios'

export class AppError extends AxiosError {
	status?: number
	errorcode?: string
	constructor({
		requestError,
	}: {
		requestError?: {
			message?: string
			name?: string
			response?: {
				status?: number
				data?: { errorcode?: string; message?: string; ['invalid-arguments']?: [{ x: string }]; [x: string]: any }
			}
			[x: string]: any
		}
	}) {
		const { message, response, name } = requestError ?? {}
		const invalidArguments = response?.data?.[`invalid-arguments`]
		super()

		this.name = name ?? 'AppError'
		this.message = response?.data?.message ?? message ?? APPLICATION_STRINGS.INTERNAL_SERVER_ERROR
		if (invalidArguments) {
			Object.assign(this, { invalidArguments: invalidArguments ?? [] })
		}

		this.status = response?.status ?? 500
		this.errorcode = response?.data?.errorcode ?? 'intern-error'
		if (response) {
			Object.assign(this, { response })
		}
	}
}
