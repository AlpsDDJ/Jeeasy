import { getDictItems } from '@/services/ant-design-pro/api'
import type { ProColumns } from '@ant-design/pro-table'

async function formatDictItems(code: string){
  const resp = await getDictItems(code)
  const dicts = resp?.data
  return dicts.map(({dictCode, dictName}: any) => ({label: dictName, value: dictCode}))
}

export type ExtendProColumns<T> = ProColumns<T> & {
  dict?: string
}

export function columnsExtend<T>(columns: ExtendProColumns<T>[]) {
  return columns?.map(column => {
    const { renderText, dict, valueType } = column
    return {
      ...column,
      renderText: renderText || ((text, record) => record[`${column.dataIndex}_dict`] || text),
      valueType: valueType || (dict ? 'select' : 'text'),
      request: dict ? () => formatDictItems(dict) : undefined
    }
  })
}
