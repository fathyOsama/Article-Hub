import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CategoryService } from 'src/app/services/category.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ThemeService } from 'src/app/services/theme.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit{
  onAddCategory = new EventEmitter();
  onEditCategory = new EventEmitter();
  categoryForm: any = FormGroup;
  dialogAction: any = "Add";
  action: any = "Add";
  responseMessage: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBuilder : FormBuilder,
    private categoryService: CategoryService,
    public dialogRef: MatDialogRef<CategoryComponent>,
    private snackbarService: SnackbarService,
    public themeService: ThemeService,
    private ngxService: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    this.categoryForm = this.formBuilder.group({
      name: [null, [Validators.required]]
    });
    if (this.dialogData.action === 'Edit') {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.categoryForm.patchValue(this.dialogData.data);
      this.categoryForm.markAsDirty();
    }
  }

  handleSubmit() {
    if (this.dialogAction == 'Edit') {
      this.edit();
    } else {
      this.add();
    }
  }

  add() {
  this.ngxService.start();
  var data = { name: this.categoryForm.value.name };

  this.categoryService.addNewCategory(data).subscribe((response: any) => {
    this.ngxService.stop();
    this.responseMessage = response.message;
    this.snackbarService.openSnackBar(this.responseMessage);
    this.dialogRef.close(true);
  }, (error: any) => {
    this.ngxService.stop();
    this.responseMessage = error.error?.message || GlobalConstants.genericErorr;
    this.snackbarService.openSnackBar(this.responseMessage);
  });
}

edit() {
  this.ngxService.start();
  var data = {
    id: this.dialogData.data.id,
    name: this.categoryForm.value.name
  };

  this.categoryService.updateCategory(data).subscribe((response: any) => {
    this.ngxService.stop();
    this.responseMessage = response.message;
    this.snackbarService.openSnackBar(this.responseMessage);
    this.dialogRef.close(true);
  }, (error: any) => {
    this.ngxService.stop();
    this.responseMessage = error.error?.message || GlobalConstants.genericErorr;
    this.snackbarService.openSnackBar(this.responseMessage);
  });
}

}
