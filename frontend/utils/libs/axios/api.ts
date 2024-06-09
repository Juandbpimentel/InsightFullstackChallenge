import { APPLICATION_STRINGS } from '@/utils/constants/strings'
import { AppError } from '@/utils/errors/app-error'
import axios, { AxiosError, HttpStatusCode, isAxiosError } from 'axios'

export const api = axios.create({
	baseURL: process.env.NEXT_PUBLIC_INSIGHT_PUBLIC_API_URL ?? 'http://localhost:8080/api',
})

api.interceptors.response.use(
	(response) => response,
	(requestError: AxiosError<any, any> | undefined) => {
		if (isAxiosError(requestError)) {
			if (
				requestError.response?.headers.errorcode === 'unauthorized' ||
				requestError.response?.data?.errorcode === 'unauthorized'
			) {
				return Promise.reject(
					new AppError({
						requestError: {
							...requestError,
							status: HttpStatusCode.Unauthorized,
							response: {
								data: {
									errorcode: !requestError.response?.headers.errorcode ? 'invalid-credentials' : 'unauthorized',
								},
							},
						},
					})
				)
			}
			if (requestError.response?.data) {
				if (requestError.response?.data?.['invalid-arguments'])
					return Promise.reject(
						new AppError({
							requestError,
						})
					)
				return Promise.reject(new AppError({ requestError }))
			}
			return Promise.reject(
				new AppError({
					requestError: {
						...requestError,
						message: APPLICATION_STRINGS.INTERNAL_SERVER_ERROR,
					},
				})
			)
		}

		return Promise.reject(requestError)
	}
)
