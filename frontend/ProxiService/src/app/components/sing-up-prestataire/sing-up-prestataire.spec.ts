import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SingUpPrestataire } from './sing-up-prestataire';

describe('SingUpPrestataire', () => {
  let component: SingUpPrestataire;
  let fixture: ComponentFixture<SingUpPrestataire>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SingUpPrestataire],
    }).compileComponents();

    fixture = TestBed.createComponent(SingUpPrestataire);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
