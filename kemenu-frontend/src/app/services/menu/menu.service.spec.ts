/* tslint:disable:no-unused-variable */

import {MenuService} from './menu.service';
import TestUtils from '../../utils/test-utils';
import {HttpErrorResponse} from '@angular/common/http';

describe('MenuService', () => {

  let httpClientSpy: { get: jasmine.Spy };
  let service: MenuService;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    service = new MenuService(httpClientSpy as any);
  });

  it('should get menu if exists', () => {
    const existingMenu = {name: 'test-menu'};

    httpClientSpy.get.and.returnValue(TestUtils.asyncData(existingMenu));

    service.getCustomer('text@example.com').subscribe(
      menu => expect(menu).toEqual(existingMenu, 'expected menu'),
      fail
    );

    expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
  });

  it('should return an error when the server returns a 404', () => {
    const errorResponse = new HttpErrorResponse({
      error: '404',
      status: 404, statusText: 'Not Found'
    });

    httpClientSpy.get.and.returnValue(TestUtils.asyncError(errorResponse));

    service.getCustomer('test@example.com').subscribe(
      heroes => fail('expected an error, not heroes'),
      error => expect(error.message).toContain('404')
    );
  });
});
