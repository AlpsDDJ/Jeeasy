import React from 'react'
import _ from 'lodash'
import ProForm, {
  ModalForm, DrawerForm,
  ProFormText,
} from '@ant-design/pro-form'

const modalTypes = {
  modal: ModalForm,
  drawer: DrawerForm,
}

const JFormModal = (props) => {
  const {
    type = 'modal', columns = [], columnsConf = {}, formCols = 3, title = '--', onClose = () => {
    }, ...formOptions
  } = props

  const JModal = modalTypes[type]

  const formOpt = _.assign(formOptions, {
    onFinish: values => {
      console.log(values)
      onClose()
    },
    title,
    onVisibleChange: onClose,
  })
  console.log(formOpt)

  // let formColsIndex = 0;
  const getFormItem = () => {
    const colsDataIndex = []
    return columns.map((col, index) => {
      // if(index === 0){
      //   formColsIndex = 0
      // }
      // formColsIndex += 1
      const { dataIndex } = col
      if (_.indexOf(colsDataIndex, dataIndex) !== -1) {
        return (<></>)
      }
      colsDataIndex.push(dataIndex)
      const itemConf = {
        name: dataIndex,
        label: col.title,
      }
      const formItems = [columnsConf?.[dataIndex]?.formRender?.(itemConf) || (<ProFormText {...itemConf} />)]
      let j = 0
      for (let i = 0; i < formCols - 1;) {
        j += 1
        const nextCol = columns[index + j]
        if (nextCol) {
          const { dataIndex: nextDataIndex } = nextCol
          console.log(nextDataIndex)
          colsDataIndex.push(nextDataIndex)
          const columnsConfElement = columnsConf[nextDataIndex]
          const nextItemConf = {
            name: nextDataIndex,
            label: nextCol.title,
            style: {width: '100%'},
          }
          // formItems.push(columnsConfElement && columnsConfElement.formRender ? columnsConfElement.formRender(nextItemConf) : <ProFormText {...nextItemConf} />)
          formItems.push(columnsConfElement?.formRender?.(nextItemConf) || <ProFormText {...nextItemConf} />)
          i += ((columnsConf[nextDataIndex] || {}).cols || 1)
        } else {
          i += 1
        }
      }

      return (
        <ProForm.Group key={`form_${col.dataIndex}`}>
          {formItems}
        </ProForm.Group>
      )
    })
  }

  // const getFormItem = () =>
  //   columns.map(col => {
  //   const { dataIndex, title } = col
  //   const itemConf = {
  //     name: dataIndex,
  //     label: title,
  //   }
  //   return (
  //     <ProForm.Group key={`form_${col.dataIndex}`}>
  //       {columnsConf[dataIndex] && columnsConf[dataIndex].formRender ? columnsConf[dataIndex].formRender(itemConf) : <ProFormText {...itemConf} />}
  //     </ProForm.Group>
  //   )
  // })

  return (
    <JModal
      {...formOpt}
    >
      {getFormItem()}
    </JModal>
  )
}

export default JFormModal
