import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderControlComponentComponent } from './header-control-component.component';

describe('HeaderControlComponentComponent', () => {
  let component: HeaderControlComponentComponent;
  let fixture: ComponentFixture<HeaderControlComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderControlComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderControlComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
