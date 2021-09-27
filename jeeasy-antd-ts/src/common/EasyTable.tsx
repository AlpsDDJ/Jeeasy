import type { MutableRefObject } from 'react'
import React, { useRef, useState } from 'react'
import { getDictItems } from '@/services/common/api'
import type { ProColumns, ProTableProps } from '@ant-design/pro-table'
import { Button, message, Popconfirm } from 'antd'
import type { ProFormColumnsType } from '@ant-design/pro-form'
import type { ApiMap } from '@/services'
import type { FormSchema } from '@ant-design/pro-form/lib/components/SchemaForm'
import type { ActionType } from '@ant-design/pro-table/lib/typing'
import type { ProFormInstance } from '@ant-design/pro-form/lib/BaseForm'
import type { ProFormLayoutType } from '@ant-design/pro-form/lib/components/SchemaForm'
import { PlusOutlined } from '@ant-design/icons'
import { defaultFormItemLayout } from '@/common/setting'

async function formatDictItems(code: string) {
  return  await getDictItems(code)
  // const dicts = resp?.data
  // return dicts.map(({ dictCode, dictName }: any) => ({ label: dictName, value: dictCode }))
}

export type ExtendProColumns<T = any, ValueType = 'text'> = ProColumns<T> &
  ProFormColumnsType<T, ValueType> & {
  dict?: string;
  operate?: OperateColumn<T>[];
  dataIndex?: DataIndexType<T>
};


type OperateType = string | 'edit' | 'del' | 'add' | 'view' | 'enable';

type OperateRender<T> = (record: T, index?: number) => React.ReactNode;

type OperateColumn<T> = OperateType | OperateRender<T> | {
  key: OperateType;
  name: string;
  handle: (record: T, index?: number) => void;
};

type DataIndexType<T = any> = string | number | (string | number)[] | keyof T

export function columnsExtend<T, ValueType = 'text'>(columns: ExtendProColumns<T, ValueType>[], labels: FL<T> = {}, columnMap: Record<string, ExtendProColumns<T, ValueType>> = {}): ExtendProColumns<T, ValueType>[] {
  const copyColumnMap = { ...columnMap }
  columns.forEach((col) => {
    if(col.dataIndex){
      const di = typeof col.dataIndex === 'string' ? col.dataIndex : ''
      // if(di === 'status'){
      //   console.log(copyColumnMap[di])
      // }
      copyColumnMap[di] = {
        ...copyColumnMap[di],
        ...col
      }
    } else {
      copyColumnMap['operate'] = col
    }
  })

  Object.values(copyColumnMap)?.forEach((_column) => {
    const di = typeof _column.dataIndex === 'string' ? _column.dataIndex : ''
    const currCol = copyColumnMap[di]
    const column = {
      ...currCol,
      ..._column
    }

    if(!column){
      console.log(_column)
    }

    const { renderText, dict, valueType, operate } = column
    let col: ExtendProColumns<T, ValueType> = {}
    if (operate) {
      col = {
        title: '操作',
        dataIndex: 'option',
        valueType: 'option',
        render: (dom, entity, index) => {
          return operate.map((opt) => {
            if (typeof opt === 'function') {
              return opt(entity, index)
            }

            let o: {
              key: OperateType;
              name: string;
              handle: (record: T, index?: number) => void;
            }

            if (typeof opt === 'string') {
              o = {
                key: opt,
                name: opt,
                handle: () => {
                }
              }
            } else {
              o = { ...opt }
            }

            const { key, name, handle } = o
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
                    <Button type="link" danger key={ key }>
                      { name }
                    </Button>
                  </Popconfirm>
                )
              default:
                return (
                  <Button
                    type="link"
                    key={ key }
                    onClick={ () => {
                      handle(entity, index)
                    } }
                  >
                    { name }
                  </Button>
                )
            }
          })
        }
      }
    }

    if (operate || column.dataIndex === 'operate') {
      col = {
        ...col,
        hideInForm: true,
        hideInDescriptions: true,
        hideInSearch: true
      }
    }
    if (currCol || operate || column.dataIndex === 'operate') {
      copyColumnMap[di] = {
        // @ts-ignore
        title: labels[column.dataIndex],
        renderText: renderText || ((text, record) => record[`${ column.dataIndex }_dict`] || text),
        valueType: valueType || (dict ? 'select' : 'text'),
        request: !valueType && dict ? () => formatDictItems(dict) : undefined,
        ...col,
        ...column,
      }
    }
  })

  return Object.values(copyColumnMap)
}

type EasyTableConfig<T> = {
  title?: string;
  columns: ExtendProColumns<T>[];
  columnMap: Record<string, ExtendProColumns<T>>;
  apis: ApiMap<T>;
  fl: FL<T>;
  formLayout?: ProFormLayoutType;
  formatFormData?: (values: T) => any;
  // tableRef?: React.MutableRefObject<ActionType | undefined>,
  loadInfo?: boolean;
};

type FormType = '' | 'add' | 'edit' | 'view' | string;

declare type RecursivePartial<T> = T extends object ? {
  [P in keyof T]?: T[P] extends (infer U)[] ? RecursivePartial<U>[] : T[P] extends object ? RecursivePartial<T[P]> : T[P];
} : any;

type EasyTableState<T = any, ValueType = 'text'> = {
  columns: ExtendProColumns<T, ValueType>[];
  // fl: FieldsAndLabels<T>,
  tableOptions: ProTableProps<T, PageParams, ValueType>;
  tableRef: MutableRefObject<ActionType | undefined>;
  formRef: MutableRefObject<ProFormInstance<T> | undefined>;
  formOptions: FormSchema<T, ValueType>;
  formType: FormType;
  // formVisible: boolean,
  formData: T | {};
  setFormVisible: (b: boolean) => void;
  setFormData: (data: RecursivePartial<T>) => void;
  showForm: (type: FormType, data?: T | any, call?: () => {}) => void;
};

const defaultState: any = {
  formVisible: false,
  formData: {},
  formType: ''
}

export function useEasyTable<T, ValueType = 'text'>(config: EasyTableConfig<T>): EasyTableState<T> {
  const { apis, columns, fl, title, loadInfo = false, formLayout = 'DrawerForm', formatFormData = vals => vals, columnMap } = config
  const tref = useRef<ActionType>()
  const fref = useRef<ProFormInstance<T>>()

  const [state, setState] = useState(defaultState)
  const setEasyTableState = (data: Record<any, any>) => {
    setState({ ...state, ...data })
  }
  const cols = columnsExtend<T, ValueType>(columns, fl, columnMap)

  const showForm: (type: FormType, data?: T | any, call?: () => {}) => void = async (
    type,
    data = {},
    call = undefined
  ) => {
    const sta = {
      ...state,
      formType: type,
      formVisible: true,
      formData: data
    }
    if (loadInfo) {
      sta.formData = await apis.info(data?.id)
    }
    await setEasyTableState(sta)
    fref.current?.setFieldsValue(sta.formData)
    if (call) {
      await call()
    }
  }

  return {
    ...state,
    columns: cols,
    tableRef: tref,
    tableOptions: {
      rowKey: 'id',
      request: apis.list,
      actionRef: tref,
      headerTitle: title,
      toolbar: {
        actions: (
          <Button
            type="primary"
            onClick={ () => {
              showForm('add')
            } }
          >
            <PlusOutlined /> 新增
          </Button>
        )
      }
    },
    formRef: fref,
    formOptions: {
      ...defaultFormItemLayout,
      layout: 'horizontal',
      visible: state.formVisible,
      layoutType: formLayout,
      formRef: fref,
      onVisibleChange: (visible) => {
        setEasyTableState({ formVisible: visible })
        if (!visible) {
          fref.current?.resetFields()
        }
      },
      onFinish: async (values) => {
        if (state.formType !== 'edit' && state.formType !== 'add') {
          return
        }
        const params = await formatFormData({ ...state.formData, ...values })
        const rep = state.formType === 'edit' ? apis.edit : apis.add
        const { success, message: msg } = await rep(params)
        if (success) {
          message.success(msg)
          tref.current?.reload()
          setEasyTableState({ formVisible: false })
        } else {
          message.error(msg)
        }
      }
    },
    setFormVisible: async (visible) => {
      await setEasyTableState({ formVisible: visible })
    },
    setFormData: async (data) => {
      fref.current?.setFieldsValue(data)
    },
    showForm
  }
}
