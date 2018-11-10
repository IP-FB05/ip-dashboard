import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DokumenteComponent } from './dokumente.component';

describe('DokumenteComponent', () => {
  let component: DokumenteComponent;
  let fixture: ComponentFixture<DokumenteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DokumenteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DokumenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
