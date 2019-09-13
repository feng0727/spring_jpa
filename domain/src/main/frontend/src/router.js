import React from 'react'
import { HashRouter, Switch, Route } from 'react-router-dom'
import { Provider } from 'mobx-react'
import { LocaleProvider } from '@uyun/components'
import enUS from '@uyun/components/lib/locale-provider/en_US'

import getCookie from './utils/cookies'
import { getRouterData } from './common/router'
import { stores } from './stores'

const locale = getCookie('language') === 'en_US' ? enUS : undefined

function RouterConfig (props) {
  const routerData = getRouterData()
  const BasicLayout = routerData['/'].component

  return (
    <Provider {...stores}>
      <LocaleProvider locale={locale}>
        <HashRouter>
          <Switch>
            <Route path='/' component={BasicLayout} />
          </Switch>
        </HashRouter>
      </LocaleProvider>
    </Provider>
  )
}

export default RouterConfig
