import {
  observable,
  action,
  runInAction
} from 'mobx'
import moment from 'moment'
import {handleAsyncData} from '../utils/utils'

import {
  deleteTodoById,
  insertTodo,
  queryDutys,
  selectTodoFindAll,
  updateTodoById,
  queryByContent,
  priviRequests
} from '../utils/swagger-request'
import BaseList from './BaseList'

class Rota extends BaseList {
  /* 定义前端本地数据 */
  @observable curDate = moment().format('YYYY-MM-DD')
  @observable todoList = []
  @observable dutyList = []
  @observable content = ''
  @observable dealsUser = ''
  @observable params = {}
  @observable codeResult = []
  @observable is = false

  request (date = this.curDate) {
    return selectTodoFindAll(moment(date).valueOf())
  }

  @action
  async privilegesManage (params) {
    const result = await priviRequests(params)
    handleAsyncData(() => {
      const arr = []
      for (const key of result) {
        arr.push(key.code)
      }
      this.is = arr.indexOf('task_assign') > -1
      this.codeResult = arr
    })
  }

  @action
  changeSearch (type, value) {
    this[type] = value
  }

  @action
  paramsStore (text, record) {
    runInAction(() => {
      this.params = record
    })
  }

  /** 定义前端操作 */
  @action
  setCurDate (val) {
    this.curDate = val
  }

  @action
  async getDutyList (date = this.curDate) {
    const result = await queryDutys(moment(date).valueOf())
    handleAsyncData(() => {
      this.dutyList = result.body
    })
  }
  @action
  async getTodoList (date = this.curDate) {
    const result = await selectTodoFindAll(moment(date).valueOf())
    handleAsyncData(() => {
      this.todoList = result.body
    })
  }

  @action
  async getInquire (date = this.curDate) {
    const result = await queryByContent(moment(date).valueOf(), this.content)
    handleAsyncData(() => {
      this.todoList = result.body
    })
  }

  @action
  async addTodo (todo) {
    const date = this.curDate ? this.curDate : moment().format('YYYY-MM-DD')
    await insertTodo({'createTodo': {content: todo, createTime: moment(date).valueOf()}})
    handleAsyncData(() => {
      this.getTodoList()
    })
  }

  @action
  async updateTodo (params) {
    await updateTodoById({'updateTodo': params})
    handleAsyncData(() => {
      this.getTodoList()
    })
  }

  @action
  async removeTodo (id) {
    await deleteTodoById(id)
    handleAsyncData(() => {
      this.getTodoList()
    })
  }
}

export default Rota
