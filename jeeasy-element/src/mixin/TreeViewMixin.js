// import ListViewMixin from '@/mixin/ListViewMixin'
//
// const treeViewMixin = Object.assign({ }, {
//   // extend: ListViewMixin,
//   components: {
//     ...ListViewMixin.components
//   },
//   data() {
//     return {
//       ...ListViewMixin.data(),
//       rowKey: 'id',
//       childrenKey: 'children',
//       hasChildrenKey: 'hasChildren'
//     }
//   },
//   computed: {
//     ...ListViewMixin.computed,
//     tableConfig: {
//       columns: this.columns,
//       data: this.list,
//       loading: this.loading[this.api.list],
//       showSelection: this.showSelection,
//       rowKey: this.rowKey,
//       lazy: true
//     }
//   },
//   methods: {
//     ...ListViewMixin.methods,
//     loadTreeData(param = 0, treeNode, resolve) {
//       if (typeof param === 'object') {
//         const { id } = param
//         this.loadData({ parentId: id }).then(({ result }) => {
//           console.log(result)
//           resolve(result)
//         })
//       } else {
//         this.init({ parentId: param })
//       }
//     }
//   }
// })
//
// export default treeViewMixin
