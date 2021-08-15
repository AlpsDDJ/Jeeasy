
export function initColumns(fields, labels, { listHidden = [], formHidden = [], showQuery = [] }) {
  return Object.values(fields).map(field => {
    return {
      label: labels[field],
      key: field,
      hidden: listHidden.includes(field),
      form: !formHidden.includes(field),
      search: showQuery.includes(field)
    }
  })
}