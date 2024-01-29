import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBorrowComponent } from './manage-borrow.component';

describe('ManageBorrowComponent', () => {
  let component: ManageBorrowComponent;
  let fixture: ComponentFixture<ManageBorrowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageBorrowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManageBorrowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
