import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessesDialogComponent } from './processes-dialog.component';

describe('ProcessesDialogComponent', () => {
  let component: ProcessesDialogComponent;
  let fixture: ComponentFixture<ProcessesDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessesDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
