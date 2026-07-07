import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ThemeService } from '../services/theme.service';

@Component({
  selector: 'app-article-detials',
  templateUrl: './article-detials.component.html',
  styleUrls: ['./article-detials.component.scss']
})
export class ArticleDetialsComponent {

  articleDetials: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    public themeService: ThemeService
  ) {
    this.articleDetials = this.dialogData.data;
  }
}
