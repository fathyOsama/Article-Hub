import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ArticleService } from 'src/app/services/article.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ThemeService } from 'src/app/services/theme.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { ArticleComponent } from '../dialog/article/article.component';
import { ViewArticleComponent } from '../dialog/view-article/view-article.component';

@Component({
  selector: 'app-manage-article',
  templateUrl: './manage-article.component.html',
  styleUrls: ['./manage-article.component.scss']
})
export class ManageArticleComponent implements OnInit {

  displayedColumns: string[] = ['title', 'categoryName', 'status', 'publication_date', 'edit'];
  dataSource: any;
  responseMessage: any;

  constructor(
    private ngxService: NgxUiLoaderService,
    private dialog: MatDialog,
    private snackbarService: SnackbarService,
    private router: Router,
    public themeService: ThemeService,
    private articleService: ArticleService
  ) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.articleService.getAllArticle().subscribe((response: any) => {
      this.ngxService.stop();
      this.dataSource = new MatTableDataSource(response);
    }, (error: any) => {
      this.ngxService.stop();
      console.log(error);
      this.responseMessage = error.error?.message || GlobalConstants.genericErorr;
      this.snackbarService.openSnackBar(this.responseMessage);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleAddAction() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { action: 'Add' };
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(ArticleComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) this.tableData();
    });
  }

  handleViewAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { action: 'View', data: values };
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(ViewArticleComponent, dialogConfig);
  }

  handleEditAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { action: 'Edit', data: values };
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(ArticleComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) this.tableData();
    });
  }

  onDelete(value: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'Delete "' + value.title + '" article?'
    };

    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);

    dialogRef.componentInstance.onEmitStatusChange.subscribe(() => {
      dialogRef.close();
      this.ngxService.start();
      this.deleteArticle(value.id);
    });

    dialogRef.afterClosed().subscribe(() => {
      // cleanup if needed
    });
  }

  deleteArticle(id: any) {
    this.articleService.deleteArticle(id).subscribe((response: any) => {
      this.ngxService.stop();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage);
      this.tableData();
    }, (error: any) => {
      this.ngxService.stop();
      console.log(error);
      this.responseMessage = error.error?.message || GlobalConstants.genericErorr;
      this.snackbarService.openSnackBar(this.responseMessage);
    });
  }
}
