import React from 'react'
import { getDictItems } from '@/services/ant-design-pro/api'
import type { ProColumns } from '@ant-design/pro-table'
import type { FL } from '@/utils/common'
import { Button, Popconfirm } from 'antd'
import type { ProFormColumnsType } from '@ant-design/pro-form'

async function formatDictItems(code: string) {
  const resp = await getDictItems(code)
  const dicts = resp?.data
  return dicts.map(({ dictCode, dictName }: any) => ({ label: dictName, value: dictCode }))
}

export type ExtendProColumns<T, ValueType = 'text'> = ProColumns<T> & ProFormColumnsType<T, ValueType> & {
  dict?: string,
  option?: OptionColumn<T>[]
}

type Option = string | 'edit' | 'del' | 'add' | 'view' | 'enable'

type OptionRender<T> = (record: T, index?: number) => React.ReactNode

type OptionColumn<T> = OptionRender<T> | {
  key: Option,
  name: string,
  handle: (record: T, index?: number) => void,
}

// type OptionConfig<RecordType> = {
//   render?: (value: any, record: RecordType, index: number) => React.ReactNode | RenderedCell<RecordType>;
//   actions?: Record<string, {
//     name: string,
//     handle: (value: any, record: RecordType, index?: number, action?: ProCoreActionType) => void
//   }>
// }

export function columnsExtend<T>(columns: ExtendProColumns<T>[], labels: FL<T> = {}): ExtendProColumns<T>[] {
  return columns?.map(column => {
    const { renderText, dict, valueType, option } = column
    let col: ExtendProColumns<T> = {}
    if (option) {
      col = {
        title: '操作',
        dataIndex: 'option',
        valueType: 'option',
        render: (dom, entity, index) => {
          return option.map((opt) => {

            if(typeof opt === 'function') {
              return opt(entity, index)
            }

            const { key, name, handle } = opt
            switch (key) {
              case 'del':
                return (
                  <Popconfirm
                    key={ key }
                    title="确定删除此行数据?"
                    onConfirm={ () => {
                      handle(entity, index)
                    } }
                    okText="确定"
                    cancelText="取消"
                  >
                    <Button type="link" danger key={ key }>{ name }</Button>
                    </Popconfirm>
                )
              default:
                return (
                  <Button type="link" key={ key } onClick={ () => {
                    handle(entity, index)
                  } }>{ name }</Button>
                )
            }


          })
        }
      }
    }

    if(option || column.dataIndex === 'option'){
      col = {
        ...col,
        hideInForm: true,
        hideInDescriptions: true,
        hideInSearch: true
      }
    }
    return {
      // @ts-ignore
      title: labels[column.dataIndex],
      ...col,
      ...column,
      renderText: renderText || ((text, record) => record[`${ column.dataIndex }_dict`] || text),
      valueType: valueType || (dict ? 'select' : 'text'),
      request: dict ? () => formatDictItems(dict) : undefined
    }
  })
}

export function l2f<T>(labels: FL<T>): FL<T> {
  const fields: FL<T> = {}
  Object.keys(labels).forEach(label => {
    fields[label] = label
  })
  return fields
}

type FieldsAndLabels<T> = {
  fields: FL<T>,
  labels: FL<T>,
}

export function useFl<T>(labels: FL<T>): FieldsAndLabels<T> {
  return {
    labels,
    fields: l2f<T>(labels)
  }
}
