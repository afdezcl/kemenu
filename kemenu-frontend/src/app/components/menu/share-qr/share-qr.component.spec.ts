/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ShareQrComponent } from './share-qr.component';

describe('ShareQrComponent', () => {
  let component: ShareQrComponent;
  let fixture: ComponentFixture<ShareQrComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShareQrComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareQrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
