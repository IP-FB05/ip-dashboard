import { TestBed } from '@angular/core/testing';

import { DoTaskService } from './do-task.service';

describe('DoTaskService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DoTaskService = TestBed.get(DoTaskService);
    expect(service).toBeTruthy();
  });
});
