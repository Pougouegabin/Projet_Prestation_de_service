import { TestBed } from '@angular/core/testing';
import { ProxIService } from './prox-iservice';

describe('ProxIService', () => {
  let service: ProxIService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProxIService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
