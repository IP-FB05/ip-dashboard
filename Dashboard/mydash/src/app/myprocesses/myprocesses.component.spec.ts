import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyprocessesComponent } from './myprocesses.component';

describe('MyprocessesComponent', () => {
  let component: MyprocessesComponent;
  let fixture: ComponentFixture<MyprocessesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyprocessesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyprocessesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
