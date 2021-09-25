import React, { useEffect, useState } from 'react'
import { Select } from 'antd'
import { getDictItems } from '@/services/ant-design-pro/api'

type DictSelectProps = Record<string, any> | EasyFormInputProps & {
  dictCode: string,
  multiple?: boolean,
}

const DictSelect: React.FC<DictSelectProps> = ({ value, onChange, dictCode, multiple = false, ...props }) => {

  const [options, setOptions] = useState<any[]>([])

  useEffect(() => {
    getDictItems(dictCode).then((data) => {
      setOptions(data)
    })
  }, [dictCode])

  const handleChange = (val: any) => {
    onChange?.(val)
  }
  // const v = value && Array.isArray(value) ? value : [value]

  return <Select showSearch { ...props } mode={multiple? 'multiple': undefined} value={ value } onChange={ handleChange } options={ options } />
}

export default DictSelect
