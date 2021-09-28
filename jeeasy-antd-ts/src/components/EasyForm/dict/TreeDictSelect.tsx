import React, { useEffect, useState } from 'react'
import { TreeSelect } from 'antd'
import { getTreeDictItems } from '@/services/common/api'
import type { DataNode } from 'rc-tree-select/lib/interface'

type TreeDictProps = Record<string, any> | EasyFormInputProps & {
  dictCode: string,
  multiple?: boolean,
  fullValue?: boolean,
  loadAll?: boolean
}

const TreeDictSelect: React.FC<TreeDictProps> = ({ value, onChange, dictCode, multiple = false, loadAll = true, ...props }) => {

  const [options, setOptions] = useState<DataNode[]>([])

  useEffect(() => {
    getTreeDictItems(dictCode, 0, loadAll).then((data) => {
      setOptions(data)
    })
  }, [dictCode, loadAll])

  const handleChange = (val: any[]) => {
    onChange?.(val)
  }
  // const v = value && Array.isArray(value) ? value : [value]

  const loadData = loadAll ? undefined: ({ id }: DataNode) => {
    return getTreeDictItems(dictCode, id, loadAll).then(data => {
      setOptions([...options, ...data])
    })
  }

  return <TreeSelect treeDataSimpleMode={loadAll} showSearch { ...props } multiple={ multiple } value={ value } onChange={ handleChange } loadData={ loadData } treeData={ options } />
}

export default TreeDictSelect
