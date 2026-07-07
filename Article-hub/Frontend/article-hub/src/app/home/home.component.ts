import { Component } from '@angular/core';
import { ThemeService } from '../services/theme.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { Router } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SnackbarService } from '../services/snackbar.service';
import { ArticleService } from '../services/article.service';
import { GlobalConstants } from '../shared/global-constants';
import { ArticleDetialsComponent } from '../article-detials/article-detials.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  responseMessage: any;
  articles: any;
  searchText: string = '';

  constructor(
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router,
    private articleService: ArticleService,
    public themeService: ThemeService,
  ) {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.articleService.getAllPublishedArticle().subscribe(
      (response: any) => {
        this.articles = response;
        this.ngxService.stop();
      },
      (error: any) => {
        this.ngxService.stop();
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericErorr;
        }
        this.snackbarService.openSnackBar(this.responseMessage);
      },
    );
  }

  filteredItems(): any {
    if (!this.articles) return [];
    if (!this.searchText) return this.articles;
    return this.articles.filter((item: any) =>
      item.title?.toLowerCase().includes(this.searchText.toLowerCase()) ||
      item.categoryName?.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }

  changeTheme(color: any) {
    this.themeService.setTheme(color);
  }

  handleViewAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { action: 'View', data: values };
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(ArticleDetialsComponent, dialogConfig);
  }

}
