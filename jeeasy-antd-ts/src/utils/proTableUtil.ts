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

export function columnsExtend<T>(columns: ExtendProColumns<T>[], labels: PUB.FL<T> = {}) {
  return columns?.map(column => {
    const { renderText, dict, valueType } = column
    return {
      // @ts-ignore
      title: labels[column.dataIndex],
      ...column,
      renderText: renderText || ((text, record) => record[`${column.dataIndex}_dict`] || text),
      valueType: valueType || (dict ? 'select' : 'text'),
      request: dict ? () => formatDictItems(dict) : undefined
    }
  })
}

export function l2f<T>(labels: PUB.FL<T>): PUB.FL<T> {
  const fields: PUB.FL<T> = {}
  Object.keys(labels).forEach(label => {
    fields[label] = label
  })
  return fields
}

type FieldsAndLabels<T> = {
  fields: PUB.FL<T>,
  labels: PUB.FL<T>
}

export function useFl<T>(labels: PUB.FL<T>): FieldsAndLabels<T> {
  return {
    labels,
    fields: l2f<T>(labels)
  }
}
