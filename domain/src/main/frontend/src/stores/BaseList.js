import { observable, action, runInAction } from 'mobx'

class BaseList {
  params = {}

  @observable
  loading = false

  @observable
  search = ''

  @observable
  sorter= {}

  @observable
  list=[]

  @observable
  page = 1

  @observable
  pageSize = 10

  @observable
  total = 0

  queryString () {
    return Object.keys(this.params)
      .map(key => `${key}=${this.params[key]}`)
      .join('&')
  }

  @action
  changeSearch (value) {
    this.search = value
  }

  @action
  changeTable (page, pageSize, sorter) {
    this.sorter = sorter
    this.page = page
    this.pageSize = pageSize
  }

  @action
  async getList (params = {}, ...args) {
    this.loading = true

    if (this.page) {
      params.page = this.page
    }

    if (this.pageSize) {
      params.pageSize = this.pageSize
    }

    if (this.sorter.columnKey && this.sorter.order) {
      params.sortField = this.sorter.columnKey
      params.order = this.sorter.order === 'descend' ? 1 : 0
    }

    try {
      const data = await this.request(params, ...args)
      runInAction(() => {
        this.loading = false
        this.list = data.body.data
        this.total = parseInt(data.body.total)
      })
    } catch (e) {
      runInAction(() => {
        this.loading = false
      })
    }
  }
}

export default BaseList
