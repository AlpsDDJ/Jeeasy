import type { MutableRefObject } from 'react';
import React, { useRef, useState } from 'react';
import { getDictItems } from '@/services/ant-design-pro/api';
import type { ProColumns, ProTableProps } from '@ant-design/pro-table';
import type { FL, PageParams } from '@/utils/common';
import { Button, Popconfirm } from 'antd';
import type { ProFormColumnsType } from '@ant-design/pro-form';
import type { ApiMap } from '@/services';
import type { FormSchema } from '@ant-design/pro-form/lib/components/SchemaForm';
import type { ActionType } from '@ant-design/pro-table/lib/typing';
import type { ProFormInstance } from '@ant-design/pro-form/lib/BaseForm';
import { ProFormLayoutType } from '@ant-design/pro-form/lib/components/SchemaForm';

async function formatDictItems(code: string) {
  const resp = await getDictItems(code);
  const dicts = resp?.data;
  return dicts.map(({ dictCode, dictName }: any) => ({ label: dictName, value: dictCode }));
}

export type ExtendProColumns<T, ValueType = 'text'> = ProColumns<T> &
  ProFormColumnsType<T, ValueType> & {
    dict?: string;
    option?: OptionColumn<T>[];
  };

type Option = string | 'edit' | 'del' | 'add' | 'view' | 'enable';

type OptionRender<T> = (record: T, index?: number) => React.ReactNode;

type OptionColumn<T> =
  | OptionRender<T>
  | {
      key: Option;
      name: string;
      handle: (record: T, index?: number) => void;
    };

// type OptionConfig<RecordType> = {
//   render?: (value: any, record: RecordType, index: number) => React.ReactNode | RenderedCell<RecordType>;
//   actions?: Record<string, {
//     name: string,
//     handle: (value: any, record: RecordType, index?: number, action?: ProCoreActionType) => void
//   }>
// }

export function columnsExtend<T, ValueType = 'text'>(
  columns: ExtendProColumns<T, ValueType>[],
  labels: FL<T> = {},
): ExtendProColumns<T, ValueType>[] {
  return columns?.map((column) => {
    const { renderText, dict, valueType, option } = column;
    let col: ExtendProColumns<T, ValueType> = {};
    if (option) {
      col = {
        title: '操作',
        dataIndex: 'option',
        valueType: 'option',
        render: (dom, entity, index) => {
          return option.map((opt) => {
            if (typeof opt === 'function') {
              return opt(entity, index);
            }

            const { key, name, handle } = opt;
            switch (key) {
              case 'del':
                return (
                  <Popconfirm
                    key={key}
                    title="确定删除此行数据?"
                    onConfirm={() => {
                      handle(entity, index);
                    }}
                    okText="确定"
                    cancelText="取消"
                  >
                    <Button type="link" danger key={key}>
                      {name}
                    </Button>
                  </Popconfirm>
                );
              default:
                return (
                  <Button
                    type="link"
                    key={key}
                    onClick={() => {
                      handle(entity, index);
                    }}
                  >
                    {name}
                  </Button>
                );
            }
          });
        },
      };
    }

    if (option || column.dataIndex === 'option') {
      col = {
        ...col,
        hideInForm: true,
        hideInDescriptions: true,
        hideInSearch: true,
      };
    }
    return {
      // @ts-ignore
      title: labels[column.dataIndex],
      ...col,
      ...column,
      renderText: renderText || ((text, record) => record[`${column.dataIndex}_dict`] || text),
      valueType: valueType || (dict ? 'select' : 'text'),
      request: dict ? () => formatDictItems(dict) : undefined,
    };
  });
}

export function l2f<T>(labels: FL<T>): FL<T> {
  const fields: FL<T> = {};
  Object.keys(labels).forEach((label) => {
    // @ts-ignore
    fields[label] = label;
  });
  return fields;
}

type FieldsAndLabels<T> = {
  fields: FL<T>;
  labels: FL<T>;
};

export function useFl<T>(labels: FL<T>): FieldsAndLabels<T> {
  return {
    labels,
    fields: l2f<T>(labels),
  };
}

type EasyTableConfig<T> = {
  title?: string;
  columns: ExtendProColumns<T>[];
  apis: ApiMap<T>;
  fl: FL<T>;
  formLayout?: ProFormLayoutType;
  // tableRef?: React.MutableRefObject<ActionType | undefined>,
  loadInfo?: boolean;
};

type FormType = '' | 'add' | 'edit' | 'view' | string;

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
  setFormData: (data: T) => void;
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
  const tref = useRef<ActionType>();
  const fref = useRef<ProFormInstance<T>>();

  const defaultState: any = {
    formVisible: false,
    formData: {},
    formType: '',
  };

  const [state, setState] = useState(defaultState);
  const setEasyTableState = (data: Record<any, any>) => {
    setState({ ...state, ...data });
  };

  // const [formVisible, setFormVisible] = useState<boolean>(false)
  // const [formData, setFormData] = useState<T | {}>({})
  // const [formType] = useState<FormType>('')

  const { apis, columns, fl, title, loadInfo = false, formLayout = 'DrawerForm' } = config;
  // let formVisible: boolean = false
  const cols = columnsExtend<T, ValueType>(columns, fl);
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
    call = undefined,
  ) => {
    const state_ = {
      ...state,
      formType: type,
      formVisible: true,
      formData: data,
    };
    if (loadInfo) {
      state_.formData = await apis.info(data?.id);
    }
    await setEasyTableState(state_);
    fref.current?.setFieldsValue(state_.formData);
    if (call) {
      await call();
    }
  };

  return {
    // ...defaultState,
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
            onClick={() => {
              showForm('add');
            }}
          >
            新增
          </Button>
        ),
      },
    },
    formRef: fref,
    formOptions: {
      visible: state.formVisible,
      layoutType: formLayout,
      formRef: fref,
      onVisibleChange: (visible) => {
        setEasyTableState({ formVisible: visible });
        if (!visible) {
          fref.current?.resetFields();
          // setState({...state, formData: {}})
        }
      },
      // initialValues: state.formData
    },
    setFormVisible: async (visible) => {
      await setEasyTableState({ formVisible: visible });
    },
    setFormData: async (data) => {
      fref.current?.setFieldsValue(data);
    },
    showForm,
  };
}
