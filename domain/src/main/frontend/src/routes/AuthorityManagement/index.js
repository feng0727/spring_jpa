import React, { Component } from 'react'
import AuthorityManagement from '@uyun/ec-authority-management'
import { APP_NAME } from '../../conf'

class Authority extends Component {
  render () {
    return (
      <div style={{padding: 24, height: '100%'}}>
        <AuthorityManagement appName={APP_NAME} />
      </div>
    )
  }
}

export default Authority
