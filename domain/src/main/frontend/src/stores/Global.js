import { observable, action } from 'mobx'

import { getLanguage } from '~/utils/utils'

class Global {
  @observable
  language = getLanguage()

  @observable
  theme = 'blue'

  themes = ['white', 'dark', 'blue']

  @action
  changeLanguage () {
    this.language = this.language === 'zh_CN' ? 'en_US' : 'zh_CN'
  }

  @action
  changeTheme () {
    this.theme = this.themes.shift()

    this.themes.push(this.theme)
  }
}

export default Global
