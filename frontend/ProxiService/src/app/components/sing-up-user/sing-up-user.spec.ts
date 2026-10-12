import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SingUpUser } from './sing-up-user';

describe('SingUpUser', () => {
  let component: SingUpUser;
  let fixture: ComponentFixture<SingUpUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SingUpUser],
    }).compileComponents();

    fixture = TestBed.createComponent(SingUpUser);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
