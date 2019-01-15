import { TestBed } from '@angular/core/testing';

import { SubsService } from './subs.service';

describe('SubsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SubsService = TestBed.get(SubsService);
    expect(service).toBeTruthy();
  });
});
