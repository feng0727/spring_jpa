import React from 'react'
import {Calendar, Title, Card} from '@uyun/components'
import moment from 'moment'
import {observer, inject} from 'mobx-react'
import { __ } from '~/utils/i18n'

@inject('todoStore')
@observer
class TodoCalendar extends React.Component {
  onSelect = value => {
    const curDate = moment(value).format('YYYY-MM-DD')
    this.props.todoStore.setCurDate(curDate)
    this.props.todoStore.getDutyList(curDate)
    this.props.todoStore.getTodoList(curDate)
  }
  render () {
    return (
      <div>
        <Title>{__('rota-todo-calendar')}</Title>
        <Card>
          <Calendar fullscreen={false} onSelect={this.onSelect} />
        </Card>
      </div>
    )
  }
}
export default TodoCalendar
