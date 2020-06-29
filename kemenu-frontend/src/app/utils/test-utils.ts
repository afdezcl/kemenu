import {defer} from 'rxjs';

export default class TestUtils {
  static asyncData<T>(data: T) {
    return defer(() => Promise.resolve(data));
  }

  static asyncError<T>(errorObject: any) {
    return defer(() => Promise.reject(errorObject));
  }
}
