import { TestBed, ComponentFixture } from '@angular/core/testing';
import { AlertComponent } from './alert.component';
import { RouterModule } from '@angular/router';

describe('Component: Alert', () => {

  let component: AlertComponent;
  let fixture: ComponentFixture<AlertComponent>;
  const SUCCESS_TEXT = 'Welcome to KEMENU';
  const SUCCESS_TYPE = 'success';
  const DANGER_TEXT = 'Email or password wrong';
  const DANGER_TYPE = 'danger';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([])],
      declarations: [AlertComponent]
    });

    fixture = TestBed.createComponent(AlertComponent);
    component = fixture.componentInstance;
  });

  it('Shout create the component alert', () => {
    expect(component).toBeTruthy();
  });

  it('Should appear message in success type', () => {
    component.message = {text: SUCCESS_TEXT, type: SUCCESS_TYPE};
    fixture.detectChanges();
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('.message').textContent).toBe(SUCCESS_TEXT);
  });

  it('Should appear message in danger type', () => {
    component.message = {text: DANGER_TEXT, type: DANGER_TYPE};
    fixture.detectChanges();
    const compiled: HTMLElement = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('.message').textContent).toBe(DANGER_TEXT);
  });

});
