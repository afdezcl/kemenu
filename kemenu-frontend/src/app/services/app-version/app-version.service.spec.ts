/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AppVersionService } from './app-version.service';

describe('Service: AppVersion', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AppVersionService]
    });
  });

  it('should ...', inject([AppVersionService], (service: AppVersionService) => {
    expect(service).toBeTruthy();
  }));
});
