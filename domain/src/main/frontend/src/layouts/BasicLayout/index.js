import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import PropTypes from 'prop-types'
import { Route, Redirect, Switch, Link } from 'react-router-dom'
import NotFound from '~/routes/NotFound'
import { APP_NAME } from '../../conf'

import { getRedirects, getRoutes } from '~/utils/utils'
import BasicLayout from '@uyun/ec-basic-layout'

import './index.less'

@inject('globalStore', 'todoStore')
@observer
class BasicLayouts extends Component {
  static childContextTypes = {
    location: PropTypes.object,
    routerData: PropTypes.object
  }

  state = {
    collapsed: false
  }

  constructor (props) {
    super(props)
  }

  getChildContext () {
    const { location, routerData } = this.props
    return {
      location,
      routerData
    }
  }

  handleCollapse = collapsed => {
    this.setState({ collapsed })
  }

  componentDidMount () {
    this.props.todoStore.privilegesManage({app_name: APP_NAME})
  }

  render () {
    const {
      match,
      menuData,
      routerData
    } = this.props
    const redirects = getRedirects(match.path, routerData)
      .map(item => <Redirect {...item} />)

    const routes = getRoutes(match.path, routerData)
      .map(item => <Route {...item} />)

    return (
      <BasicLayout
        sideMenu={{ items: menuData, Link: Link }}
        productName={APP_NAME}
      >
        <Switch>
          {redirects}
          {routes}
          <Route render={NotFound} />
        </Switch>
      </BasicLayout>
    )
  }
}

export default BasicLayouts
