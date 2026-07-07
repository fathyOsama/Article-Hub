import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppUserService } from '../services/app-user.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { ThemeService } from '../services/theme.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  loginForm:any = FormGroup;
  responseMessamge: any;

  constructor (
    private formBulider: FormBuilder,
    private router: Router,
    private appuserService: AppUserService,
    private ngxService: NgxUiLoaderService,
    private snackbarService: SnackbarService,
    public themeService: ThemeService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBulider.group({
      email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      password: [null,[Validators.required]]
    })
  }

  handleSubmit(){
    this.ngxService.start();
    var formData = this.loginForm.value;
    var data = {
      email:formData.email,
      password: formData.password
    }

    this.appuserService.login(data).subscribe((response:any) => {
      this.ngxService.stop();
      localStorage.setItem('token', response.token);
      this.router.navigate(['articleHub/dashboard']);
    },(error) => {
      console.log(error);
      this.ngxService.stop();
      if (error.error?.message) {
        this.responseMessamge = error.error?.message;
      } else {
        this.responseMessamge = GlobalConstants.genericErorr;
      }
      this.snackbarService.openSnackBar(this.responseMessamge);
    });
  }

  onBack() {
    this.router.navigate(['/']);
  }
}
