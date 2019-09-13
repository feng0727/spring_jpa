import isAbsoluteURL from 'axios/lib/helpers/isAbsoluteURL'

import { __ } from '~/utils/i18n'

const menuData = [
  {
    key: 'todo',
    name: __('menu-todo'),
    type: 'link',
    icon: 'calendar',
    path: 'todo'
  },
  {
    key: 'task',
    name: __('menu-task'),
    type: 'link',
    icon: 'bars',
    path: 'task'
  },
  {
    key: 'authority-management',
    name: __('menu-authority-management'),
    type: 'link',
    icon: 'setting',
    path: `${location.origin}/userrole`,
    target: '_blank',
    below: true
  }
]

export const getMenuData = () => menuData

export function getFlatMenus (menuData, parentPath = '') {
  let keys = []

  menuData.forEach(item => {
    let { path } = item

    if (!isAbsoluteURL(path)) {
      path = `${parentPath}/${path}`
    }

    keys.push({
      ...item,
      path
    })

    if (item.children) {
      keys = keys.concat(getFlatMenus(item.children, path))
    }
  })

  return keys
}
