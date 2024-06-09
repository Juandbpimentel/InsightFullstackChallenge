export type UserToken = {
	id: string
	name: string
	email: string
	role: UserRole
}

export type Supplier = {
	id: string
	name: string
	cnpj: string
	phone: string
	email: string
	address: string
	supplyTypes: SupplyType[]
}

export enum UserRole {
	ADMIN = 'ADMIN',
	USER = 'USER',
}

export enum SupplyType {
	PRODUCTS = 'PRODUCTS',
	SERVICES = 'SERVICES',
	RAW_MATERIALS = 'RAW_MATERIALS',
	OTHERS = 'OTHERS',
}
