import { ComponentFixture, TestBed } from '@angular/core/testing';

import {PersonNewComponent} from './person-new';

describe('PersonNewComponent', () => {
  let component: PersonNewComponent;
  let fixture: ComponentFixture<PersonNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonNewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
