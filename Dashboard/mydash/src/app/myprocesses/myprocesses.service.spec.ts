import { TestBed } from '@angular/core/testing';

import { MyprocessesService } from './myprocesses.service';

describe('MyprocessesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MyprocessesService = TestBed.get(MyprocessesService);
    expect(service).toBeTruthy();
  });
});
