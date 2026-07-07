import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleDetialsComponent } from './article-detials.component';

describe('ArticleDetialsComponent', () => {
  let component: ArticleDetialsComponent;
  let fixture: ComponentFixture<ArticleDetialsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArticleDetialsComponent]
    });
    fixture = TestBed.createComponent(ArticleDetialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
