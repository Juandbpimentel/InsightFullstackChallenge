import { Supplier, SupplyType } from '@/utils/types/types'
import type { TableColumnsType, TableColumnType, TableProps, InputRef } from 'antd'
import { Button, Space, Table, Tag, Input } from 'antd'
import type { FilterDropdownProps } from 'antd/es/table/interface'
import React, { useState } from 'react'
import { SearchOutlined, EyeFilled, DeleteFilled, EditFilled } from '@ant-design/icons'

type OnChange = NonNullable<TableProps<Supplier>['onChange']>

type GetSingle<T> = T extends (infer U)[] ? U : never
type Sorts = GetSingle<Parameters<OnChange>[2]>

type DataIndex = keyof Supplier

interface SupplierTableProps {
	data: Supplier[]
	handleEdit: (id: string) => () => void
	handleDelete: (id: string) => () => void
	handleView: (id: string) => () => void
}

const SupplierTable: React.FC<SupplierTableProps> = ({ data, handleEdit, handleDelete, handleView }) => {
	const [sortedInfo, setSortedInfo] = useState<Sorts>({})
	const [searchText, setSearchText] = useState('')
	const [searchedColumn, setSearchedColumn] = useState('')
	const searchInput = React.useRef<InputRef>(null)

	const handleChange: OnChange = (pagination, filters, sorter) => {
		setSortedInfo(sorter as Sorts)
	}

	const handleSearch = (selectedKeys: string[], confirm: FilterDropdownProps['confirm'], dataIndex: DataIndex) => {
		confirm()
		setSearchText(selectedKeys[0])
		setSearchedColumn(dataIndex)
	}

	const handleReset = (clearFilters: () => void) => {
		clearFilters()
		setSearchText('')
	}

	const getColumnSearchProps = (dataIndex: DataIndex): TableColumnType<Supplier> => ({
		filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters, close }) => (
			<div style={{ padding: 8 }} onKeyDown={(e) => e.stopPropagation()}>
				<Input
					ref={searchInput}
					placeholder={`Search ${dataIndex}`}
					value={selectedKeys[0]}
					onChange={(e) => setSelectedKeys(e.target.value ? [e.target.value] : [])}
					onPressEnter={() => handleSearch(selectedKeys as string[], confirm, dataIndex)}
					style={{ marginBottom: 8, display: 'block' }}
				/>
				<Space>
					<Button
						type='primary'
						onClick={() => handleSearch(selectedKeys as string[], confirm, dataIndex)}
						icon={<SearchOutlined />}
						size='small'
						style={{ width: 90 }}
					>
						Search
					</Button>
					<Button onClick={() => clearFilters && handleReset(clearFilters)} size='small' style={{ width: 90 }}>
						Reset
					</Button>
					<Button
						type='link'
						size='small'
						onClick={() => {
							confirm({ closeDropdown: false })
							setSearchText((selectedKeys as string[])[0])
							setSearchedColumn(dataIndex)
						}}
					>
						Filter
					</Button>
					<Button
						type='link'
						size='small'
						onClick={() => {
							close()
						}}
					>
						close
					</Button>
				</Space>
			</div>
		),
		filterIcon: (filtered: boolean) => <SearchOutlined style={{ color: filtered ? '#1677ff' : undefined }} />,
		onFilter: (value, record) =>
			record[dataIndex]
				.toString()
				.toLowerCase()
				.includes((value as string).toLowerCase()),
		onFilterDropdownOpenChange: (visible) => {
			if (visible) {
				setTimeout(() => searchInput.current?.select(), 100)
			}
		},
		render: (text) => (searchedColumn === dataIndex ? (text ? text.toString() : searchText) : text),
	})

	const columns: TableColumnsType<Supplier> = [
		{
			title: 'Nome',
			dataIndex: 'name',
			key: 'name',
			sorter: (a, b) => a.name.localeCompare(b.name),
			sortOrder: sortedInfo.columnKey === 'name' ? sortedInfo.order : null,
			ellipsis: true,
			...getColumnSearchProps('name'),
		},
		{
			title: 'CNPJ',
			dataIndex: 'cnpj',
			key: 'cnpj',
			render: (text: any) => <a>{text}</a>,
			...getColumnSearchProps('cnpj'),
		},
		{
			title: 'Email',
			dataIndex: 'email',
			key: 'email',
			...getColumnSearchProps('email'),
		},
		{
			title: 'Tipo de Fornecedor',
			dataIndex: 'supplierTypes',
			key: 'supplierTypes',
			render: (_, { supplyTypes }) => (
				<>
					{supplyTypes?.map((type) => {
						let color =
							type === SupplyType.PRODUCTS
								? 'green'
								: type === SupplyType.SERVICES
								? 'blue'
								: type === SupplyType.RAW_MATERIALS
								? 'red'
								: 'gray'
						let text =
							type === SupplyType.PRODUCTS
								? 'Produtos'
								: type === SupplyType.SERVICES
								? 'Serviços'
								: type === SupplyType.RAW_MATERIALS
								? 'Matéria Prima'
								: 'Outros'
						return (
							<Tag color={color} key={type}>
								{text}
							</Tag>
						)
					})}
				</>
			),
		},
		{
			title: 'Ações',
			key: 'action',
			width: '20%',
			render: (_, record) => (
				<div className='flex flex-row justify-evenly items-center w-full'>
					<Button type='link' size='small' icon={<EyeFilled />} onClick={handleView(record.id)}></Button>
					<Button type='link' icon={<EditFilled />} onClick={handleEdit(record.id)}></Button>

					<Button type='link' icon={<DeleteFilled />} onClick={handleDelete(record.id)}></Button>
				</div>
			),
		},
	]

	return (
		<Space direction='vertical' className='m-10'>
			<Table columns={columns} dataSource={data} className='w-full' onChange={handleChange}></Table>
		</Space>
	)
}

export default SupplierTable
