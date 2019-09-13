import Cookies from 'js-cookie'

export const getCookie = Cookies.get.bind(Cookies)

export const setCookie = Cookies.set.bind(Cookies)

export default Cookies
