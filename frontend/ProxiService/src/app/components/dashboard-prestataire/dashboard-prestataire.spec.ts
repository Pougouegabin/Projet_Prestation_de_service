import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardPrestataire } from './dashboard-prestataire';

describe('DashboardPrestataire', () => {
  let component: DashboardPrestataire;
  let fixture: ComponentFixture<DashboardPrestataire>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardPrestataire],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardPrestataire);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
