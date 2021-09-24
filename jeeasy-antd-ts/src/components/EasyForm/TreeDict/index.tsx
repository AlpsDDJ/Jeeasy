import React, { useEffect, useState } from 'react'
import { TreeSelect } from 'antd'
import { getTreeDictItems } from '@/services/ant-design-pro/api'
import type { DataNode } from 'rc-tree-select/lib/interface'

type TreeDictProps = Record<string, any> | EasyFormInputProps & {
  dictCode: string,
  multiple?: boolean,
  fullValue?: boolean
}

const TreeDict: React.FC<TreeDictProps> = ({ value = [], onChange, dictCode, multiple = false, ...props }) => {

  const [options, setOptions] = useState<DataNode[]>([])

  useEffect(() => {
    getTreeDictItems(dictCode).then((data) => {
      console.log(data)
      setOptions(data)
    })
  }, [])

  const handleChange = (val: any[]) => {
    onChange?.(val)
  }
  const v = value && Array.isArray(value) ? value : [value]

  const loadData = ({ id }: DataNode) => {
    return getTreeDictItems(dictCode, id).then(data => {
      setOptions([...options, ...data])
    })
  }

  return <TreeSelect showSearch { ...props } multiple={ multiple } value={ v } onChange={ handleChange } loadData={ loadData } treeData={ options } />
}

export default TreeDict
