export default class Utils {

  static getBrowserLang(): string {
    if (window.navigator.language.includes('es')) {
      return 'es';
    } else {
      return 'en';
    }
  }

}
