import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DokumenteDialogComponent } from './dokumente-dialog.component';

describe('DokumenteDialogComponent', () => {
  let component: DokumenteDialogComponent;
  let fixture: ComponentFixture<DokumenteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DokumenteDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DokumenteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
