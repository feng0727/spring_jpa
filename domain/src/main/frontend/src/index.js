import 'es6-promise/auto'
import 'babel-polyfill'
import React from 'react'
import ReactDOM from 'react-dom'
import { configure } from 'mobx'
import { AppContainer } from 'react-hot-loader'
import moment from 'moment'
import 'moment/locale/zh-cn'
import '../src/utils/login'

import App from './router'

import './index.less'

moment.locale('zh-cn')
moment.defaultFormat = 'YYYY-MM-DD HH:mm'

if (process.env.NODE_ENV !== 'production') {
  // require('./utils/mock')
}

configure({
  enforceActions: process.env.NODE_ENV !== 'production' ? 'always' : 'never'
})

function render (Component) {
  ReactDOM.render(
    <AppContainer>
      <Component />
    </AppContainer>,
    document.getElementById('root')
  )
}

render(App)

if (module.hot) {
  module.hot.accept(App, () => {
    render(App)
  })
}
