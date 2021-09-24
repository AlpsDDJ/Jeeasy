import type { MutableRefObject } from 'react'
import React, { useRef, useState } from 'react'
import { getDictItems } from '@/services/ant-design-pro/api'
import type { ProColumns, ProTableProps } from '@ant-design/pro-table'
import { Button, message, Popconfirm } from 'antd'
import type { ProFormColumnsType } from '@ant-design/pro-form'
import type { ApiMap } from '@/services'
import type { FormSchema } from '@ant-design/pro-form/lib/components/SchemaForm'
import type { ActionType } from '@ant-design/pro-table/lib/typing'
import type { ProFormInstance } from '@ant-design/pro-form/lib/BaseForm'
import type { ProFormLayoutType } from '@ant-design/pro-form/lib/components/SchemaForm'
import { PlusOutlined } from '@ant-design/icons'

async function formatDictItems(code: string) {
  const resp = await getDictItems(code)
  const dicts = resp?.data
  return dicts.map(({ dictCode, dictName }: any) => ({ label: dictName, value: dictCode }))
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
      if(di === 'status'){
        console.log(copyColumnMap[di])
      }
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
        // ...currCol,
        ...col,
        ...column,
      }
    } else {
      console.log(column.dataIndex)
    }
  })

  // console.log(copyColumnMap)
  //
  return Object.values(copyColumnMap)
}

// export function l2f<T>(labels: FL<T>): FL<T> {
//   const fields: FL<T> = {};
//   Object.keys(labels).forEach((label) => {
//     // @ts-ignore
//     fields[label] = label;
//   });
//   return fields;
// }

// type FieldsAndLabels<T> = {
//   fields: FL<T>;
//   labels: FL<T>;
// };

// export function useFl<T>(labels: FL<T>): FieldsAndLabels<T> {
//   return {
//     labels,
//     fields: l2f<T>(labels),
//   };
// }

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

// type Syr<T> = {
//   formRef: React.MutableRefObject<ProFormInstance<T> | undefined>
//   formType: '' | 'add' | 'edit' | 'view' | string
//   tableRef: React.MutableRefObject<ActionType | undefined>
//   formOptions: {
//     initialValues: [({} | T), React.Dispatch<React.SetStateAction<{} | T>>][0]
//     columns: any[]
//     layoutType: string
//     onVisibleChange: (visible) => void
//   }
//
//   formVisible: boolean
//   columns: ExtendProColumns < T, ValueType > []
//   setFormData: (data) => Promise<void>
//   tableOptions: {
//     request: (data?: any, options?: any) => Promise<Partial<{ data: T[] | undefined; success?: boolean; total?: number } & Record<string, any>>>
//     actionRef: React.MutableRefObject<ActionType | undefined> | undefined
//     rowKey: string
//     headerTitle: string | undefined
//   }
//
//   formData: [({} | T), React.Dispatch<React.SetStateAction<{} | T>>][0]
//   setFormVisible: (b) => void
// }

export function useEasyTable<T, ValueType = 'text'>(config: EasyTableConfig<T>): EasyTableState<T> {
  const tref = useRef<ActionType>()
  const fref = useRef<ProFormInstance<T>>()

  const defaultState: any = {
    formVisible: false,
    formData: {},
    formType: ''
  }

  const [state, setState] = useState(defaultState)
  const setEasyTableState = (data: Record<any, any>) => {
    setState({ ...state, ...data })
  }

  // const [formVisible, setFormVisible] = useState<boolean>(false)
  // const [formData, setFormData] = useState<T | {}>({})
  // const [formType] = useState<FormType>('')

  const { apis, columns, fl, title, loadInfo = false, formLayout = 'DrawerForm', formatFormData = vals => vals, columnMap } = config
  // let formVisible: boolean = false
  const cols = columnsExtend<T, ValueType>(columns, fl, columnMap)
  // const tableOptions: ProTableProps<T, PageParams, ValueType> = {
  //   rowKey: 'id',
  //   request: apis.list,
  //   actionRef: tableRef,
  //   headerTitle: title,
  //   columns: cols
  // }
  // const defaultState: EasyTableState<T> = {
  //   tableRef: tableRef || tref,
  //   columns: [],
  //   formType: '',
  //   formVisible: false,
  //   formData: {}
  // }

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
      visible: state.formVisible,
      layoutType: formLayout,
      formRef: fref,
      onVisibleChange: (visible) => {
        setEasyTableState({ formVisible: visible })
        if (!visible) {
          fref.current?.resetFields()
          // setState({...state, formData: {}})
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
      // initialValues: state.formData
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
