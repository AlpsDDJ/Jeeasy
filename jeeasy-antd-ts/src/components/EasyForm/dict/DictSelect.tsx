import React, { useEffect, useState } from 'react'
import { Checkbox, Radio, Select, Switch } from 'antd'
import { getDictItems } from '@/services/common/api'
import { dictCode } from '@/common/dict'

type DictShowType = 'switch' | 'select' | 'radio' | 'radioButton' | 'checkBox'

type DictSelectProps = Record<string, any> | EasyFormInputProps & {
  dict: string,
  multiple?: boolean,
  type?: DictShowType,
}

const DictSelect: React.FC<DictSelectProps> = ({ value, onChange, dict, multiple = false, type = 'select', ...props }) => {

  const [options, setOptions] = useState<any[]>([])
  const [loading, setLoading] = useState<boolean>(false)

  useEffect(() => {
    setLoading(true)
    getDictItems(dict).then((data) => {
      setOptions(data)
      setLoading(false)
    })
  }, [])

  const handleChange = (val: any) => {
    onChange?.(val)
  }
  // const v = value && Array.isArray(value) ? value : [value]

  let showType: DictShowType = type

  if (dict === dictCode.bool || dict === dictCode.enableFlag) {
    // return <Switch
    showType = 'switch'
  }

  switch (showType) {
    case 'checkBox': {
      return <Checkbox.Group options={options} value={value} onChange={handleChange} { ...props } />
    }
    case 'switch': {
      const vals = {}
      options.forEach(({ value: v, label }) => {
        vals[v] = label
      })
      return <Switch { ...props } checked={value === 1} onChange={ (checked) => handleChange(checked ? 1: 0) } checkedChildren={vals[1]} unCheckedChildren={vals[0]} />
    }
    case 'select': {
      return <Select loading={loading} showSearch { ...props } mode={ multiple ? 'multiple' : undefined } value={ value } onChange={ handleChange } options={ options } />
    }
    case 'radio': {
      return <Radio.Group options={options} value={value} onChange={handleChange} { ...props } />
    }
    case 'radioButton': {
      return <Radio.Group options={options} value={value} onChange={handleChange} { ...props } optionType="button" />
    }
  }
}

export default DictSelect
