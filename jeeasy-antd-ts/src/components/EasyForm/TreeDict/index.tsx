import React, { useEffect, useState } from 'react'
import { Cascader } from 'antd'
import { getTreeDictItems } from '@/services/ant-design-pro/api'
import type { CascaderOptionType } from 'antd/lib/cascader'

type TreeDictProps = EasyFormInputProps & {
  dictCode: string
}

const TreeDict: React.FC<TreeDictProps> = ({ value, onChange, dictCode }) => {

  const [options, setOptions] = useState<CascaderOptionType[]>([])

  useEffect(() => {
    getTreeDictItems(dictCode).then((data) => {
      setOptions(data)
    })
  }, [])

  const handleChange = (val: any) => {

    onChange?.(val)
  }


  return <Cascader changeOnSelect showSearch  value={ value } onChange={ handleChange } loadData={ (selectedOptions) => {
    const targetOption = selectedOptions?.[selectedOptions.length - 1] || {}
    targetOption.loading = true
    getTreeDictItems(dictCode, targetOption?.value).then(data => {
      targetOption.loading = false
      targetOption.children = data
      setOptions([...options])
    })
  } } options={ options } />
}

export default TreeDict
