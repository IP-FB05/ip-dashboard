import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessesDeleteDialogComponent } from './processes-delete-dialog.component';

describe('ProcessesDeleteDialogComponent', () => {
  let component: ProcessesDeleteDialogComponent;
  let fixture: ComponentFixture<ProcessesDeleteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessesDeleteDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessesDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
