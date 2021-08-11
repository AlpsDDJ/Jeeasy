
// 自定义内容的组件
export default {
  functional: true,
  name: 'ExSlot',
  props: {
    row: Object,
    customRender: Function,
    index: Number,
    column: {
      type: Object,
      default: null
    }
  },

  render: (h, data) => {
    const params = {
      record: data.props.row,
      index: data.props.index
    }

    if (data.props.column) params.column = data.props.column
    const content = data.props.customRender(params, h)
    if (typeof content === 'object') {
      return data.props.customRender(params, h)
    }
    return h('span', content)
  }
}
