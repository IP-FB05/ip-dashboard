import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemsDialogComponent } from './systems-dialog.component';

describe('SystemsDialogComponent', () => {
  let component: SystemsDialogComponent;
  let fixture: ComponentFixture<SystemsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SystemsDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
