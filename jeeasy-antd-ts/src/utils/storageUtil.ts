const storage = window.localStorage

export function save(key: string, value: any, json?: boolean) {
  if (json) {
    storage.setItem(key, JSON.stringify(value))
  } else {
    storage.setItem(key, value)
  }
}

export function load(key: string, json?: boolean): any {
  const value = storage.getItem(key) || ''
  if (json) {
    return JSON.parse(value)
  }
  return value
}

export function remove(key: string){
  storage.removeItem(key)
}

export function clear(){
  storage.clear()
}

export default {
  save,
  load,
  remove,
  clear
}
