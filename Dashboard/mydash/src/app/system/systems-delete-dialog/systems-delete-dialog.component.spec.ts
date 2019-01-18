import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemsDeleteDialogComponent } from './systems-delete-dialog.component';

describe('SystemsDeleteDialogComponent', () => {
  let component: SystemsDeleteDialogComponent;
  let fixture: ComponentFixture<SystemsDeleteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SystemsDeleteDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemsDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
