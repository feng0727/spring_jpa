import ApiClient from '../utils/swagger-api/ApiClient'
const HttpApi = require('./swagger-api/index')
const http = new ApiClient()
http.basePath = ''

module.exports = Object.keys(HttpApi).reduce((requestFuncs, currentValue) => {
  if (currentValue.slice(3).indexOf('Api') > -1) {
    const api = new HttpApi[currentValue]()
    for (const key in api) {
      requestFuncs[key] = (...args) => new Promise((resolve, reject) => {
        const callback = (error, data, response) => {
          if (error) return reject(error)
          return resolve(response)
        }
        api[key](...args, callback)
      })
    }
    return requestFuncs
  }
  return requestFuncs
}, {})

module.exports.priviRequests = params => {
  var queryParams = {
    'app_name': params.app_name,
    'user_id': params.user_id
  }
  return new Promise(resolve => {
    const ret = http.callApi(
      'tenant/api/v2/privileges/list_by_user_id', 'GET',
      {}, queryParams, {}, {}, null, [], [], ['application/json'], null, () => {
        resolve(JSON.parse(ret.xhr.response).data)
      }
    )
  })
}
