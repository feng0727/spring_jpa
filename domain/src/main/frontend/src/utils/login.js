import { notification } from '@uyun/components'
var _send = window.XMLHttpRequest.prototype.send
var timeId = null
var timer = function (message) {
  clearTimeout(timeId)
  timeId = setTimeout(() => {
    notification.error({
      message: '错误提示信息如下：',
      description: message
    })
  }, 1000)
}

window.XMLHttpRequest.prototype.send = function (...args) {
  var _onreadystatechange = this.onreadystatechange
  this.onreadystatechange = function () {
    console.log(this.status)
    if (this.status >= 400) {
      let message
      switch (this.status) {
        case 401:
          window.location.href = 'tenant/#/login_admin?return_insite=' +
            (process.env.NODE_ENV === 'development' ? 'http://localhost:' + window.location.port + '/#/todo' : location.href);
          break
        case 403:
          message = '无操作权限'
          break
        case 400:
        case 404:
        case 502:
        case 503:
        case 504:
        case 500:
          if (this.responseText && this.responseText.indexOf('message')) {
            try {
              let msg = JSON.parse(this.responseText)
              message = msg.error.message
            } catch (e) {
              message = '请求失败，服务内部错误'
            }
          }
          break
        default :
          message = '请求失败，可能服务端异常'
          break
      }
      timer(message)
    } else {
      if (_onreadystatechange) {
        return _onreadystatechange.apply(this, args)
      }
    }
  }
  return _send.apply(this, args)
}
