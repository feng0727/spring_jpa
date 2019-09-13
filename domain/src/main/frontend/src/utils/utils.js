import { getCookie } from './cookies'
import {runInAction} from 'mobx'

export function getRedirects (path, routerData) {
  const paths = Object.keys(routerData).filter(routePath =>
    routePath.indexOf(path) === 0 && routerData[routePath].redirect)

  return paths.map(path => ({
    key: path,
    exact: true,
    from: path,
    to: routerData[path].redirect
  }))
}

export function getRoutes (path, routerData) {
  const paths = Object.keys(routerData).filter(routePath =>
    routePath.indexOf(path) === 0 && routePath !== path)

  return paths.map(path => {
    const route = routerData[path]

    return {
      key: path,
      path,
      exact: route.exact != null ? route.exact : true,
      strict: route.strict != null ? route.strict : false,
      component: route.component
    }
  })
}

export function getLanguage (language = 'zh_CN') {
  language = getCookie('language')

  if (process.env.NODE_ENV !== 'production') {
    language = localStorage.getItem('__dev_i18n__')
  }

  if (!language || language === 'undefined') {
    return 'zh_CN'
  }

  return language
}

// 封装runInAction接口
export function handleAsyncData (fn) {
  runInAction(() => {
    fn()
  })
}
