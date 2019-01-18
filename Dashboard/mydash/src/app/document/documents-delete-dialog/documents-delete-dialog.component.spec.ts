import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentsDeleteDialogComponent } from './documents-delete-dialog.component';

describe('DocumentsDeleteDialogComponent', () => {
  let component: DocumentsDeleteDialogComponent;
  let fixture: ComponentFixture<DocumentsDeleteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentsDeleteDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentsDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
