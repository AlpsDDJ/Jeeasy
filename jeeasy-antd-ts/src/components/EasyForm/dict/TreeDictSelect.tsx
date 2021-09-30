import React, { useEffect, useState } from 'react'
import { TreeSelect } from 'antd'
import { getTreeDictItems } from '@/services/common/api'
import type { DataNode } from 'rc-tree-select/lib/interface'

type TreeDictProps = Record<string, any> | EasyFormInputProps & {
  dict: string,
  multiple?: boolean,
  fullValue?: boolean,
  loadAll?: boolean
}

const TreeDictSelect: React.FC<TreeDictProps> = ({ value, onChange, dict, multiple = false, loadAll = true, ...props }) => {

  const [options, setOptions] = useState<DataNode[]>([])

  useEffect(() => {
    getTreeDictItems(dict, 0, loadAll).then((data) => {
      setOptions(data)
    })
  }, [dict, loadAll])

  const handleChange = (val: any[]) => {
    onChange?.(val)
  }
  // const v = value && Array.isArray(value) ? value : [value]

  const loadData = loadAll ? undefined: ({ id }: DataNode) => {
    return getTreeDictItems(dict, id, loadAll).then(data => {
      setOptions([...options, ...data])
    })
  }

  return <TreeSelect treeDataSimpleMode={loadAll} showSearch { ...props } multiple={ multiple } value={ value } onChange={ handleChange } loadData={ loadData } treeData={ options } />
}

export default TreeDictSelect
