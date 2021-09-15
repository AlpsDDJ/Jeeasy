import { getDictItems } from '@/services/ant-design-pro/api'

async function formatDictItems(code){
  const resp = await getDictItems(code)
  const dicts = resp?.data
  return dicts.map(({dictCode, dictName}) => ({label: dictName, value: dictCode}))
}

export function columnsExtend(columns = []) {
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
