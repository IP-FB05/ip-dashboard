import { TestBed } from '@angular/core/testing';

import { DokumentService } from './dokument.service';

describe('DokumentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DokumentService = TestBed.get(DokumentService);
    expect(service).toBeTruthy();
  });
});
