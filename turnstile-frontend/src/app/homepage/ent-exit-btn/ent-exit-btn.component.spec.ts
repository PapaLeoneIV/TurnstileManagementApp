import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntExitBtnComponent } from './ent-exit-btn.component';

describe('EntExitBtnComponent', () => {
  let component: EntExitBtnComponent;
  let fixture: ComponentFixture<EntExitBtnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntExitBtnComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EntExitBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
