import React from 'react'
import pathToRegexp from 'path-to-regexp'

import { getMenuData, getFlatMenus } from './menu'

function routeWrapper (Component) {
  return props => {
    const menuData = getMenuData()
    const routerData = getRouterData()

    return (
      <Component
        {...props}
        menuData={menuData}
        routerData={routerData}
      />
    )
  }
}

const routerConfig = {
  '/': {
    redirect: '/todo',
    component: routeWrapper(require('../layouts/BasicLayout'))
  },
  '/todo': {
    component: routeWrapper(require('../routes/Todo'))
  },
  '/task': {
    component: routeWrapper(require('../routes/Task'))
  },
  '/authority-management': {
    component: routeWrapper(require('../routes/AuthorityManagement'))
  }
}

export const getRouterData = () => {
  const menuData = getFlatMenus(getMenuData())

  const routerData = Object.keys(routerConfig).reduce((routers, path) => {
    const pathRegexp = pathToRegexp(path)
    const menuItem = menuData.find(menuItem => pathRegexp.test(menuItem.path)) || {}

    if (menuItem) {
      const router = {
        ...routerConfig[path],
        name: menuItem.name
      }

      routers[path] = router
    }

    return routers
  }, {})

  return routerData
}
