import { International } from '@uyun/everest-i18n'

import locales from '~/common/locales.json'
import { getLanguage } from './utils'

const intl = new International({
  language: getLanguage() || 'zh_CN',
  locale: locales
})

const { i18n } = intl

export {
  i18n,
  i18n as __
}

export default intl
