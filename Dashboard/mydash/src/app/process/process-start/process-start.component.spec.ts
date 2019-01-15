import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessStartComponent } from './process-start.component';

describe('ProcessStartComponent', () => {
  let component: ProcessStartComponent;
  let fixture: ComponentFixture<ProcessStartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessStartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
