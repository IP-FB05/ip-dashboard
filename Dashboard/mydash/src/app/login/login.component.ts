import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';





// Import Models
// Import Components

// Import Services
import { AuthService } from './auth/auth.service';
import { TokenStorageService } from '../login/auth/token-storage.service'
import { AuthLoginInfo } from '../login/auth/login-info';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    isLoginFailed = false;
    errorMessage = '';
    roles: string[] = [];
    private loginInfo: AuthLoginInfo;


    loginForm: FormGroup;
    submitted = false;


    constructor(private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        public snackBar: MatSnackBar,
        private tokenStorage: TokenStorageService) {


        this.loginForm = this.fb.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });


    }

    ngOnInit() {
        if (this.tokenStorage.getToken()) {
            this.authService.isLoggedin = true;
            this.roles = this.tokenStorage.getAuthorities();
        }
    }

    /*
    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        // reset login status
        this.authService.logout();

        this.authService.login(this.loginForm.controls.username.value, this.loginForm.controls.password.value).toPromise();
        /* .pipe(first())
         .subscribe(
             data => {
                 if(sessionStorage.getItem('currentUser')) {
                     this.openSnackBar("Erfolgreich angemeldet");
                 } else {
                     this.openSnackBar("Benutzer konnte nicht angemeldet werden");
                 }
             },
             error => {
                 console.log(error);
             }); 

        this.router.navigate(['/dashboard']);
    }
    */

    onSubmit() {

        this.loginInfo = new AuthLoginInfo(
            this.loginForm.controls.username.value,
            this.loginForm.controls.password.value);

        this.authService.attemptAuth(this.loginInfo).subscribe(
            data => {
                this.tokenStorage.saveToken(data.accessToken);
                this.tokenStorage.saveUsername(data.username);
                this.tokenStorage.saveAuthorities(data.authorities);

                this.isLoginFailed = false;
                this.authService.isLoggedin = true;
                this.roles = this.tokenStorage.getAuthorities();
                this.openSnackBar("Benutzer erfolgreich angemeldet");
                //this.reloadPage();
                this.router.navigate(['/dashboard']);
            },
            error => {
                console.log(error);
                this.errorMessage = error.error.message;
                this.isLoginFailed = true;
                this.openSnackBar("Benutzer konnte nicht angemeldet werden");
                window.sessionStorage.clear();
                this.authService.isLoggedin = false;

            }
        );
    }


    /*
    logout() {
        this.authService.logout();
    }
    */

    openSnackBar(text: string) {
        this.snackBar.open(text, '', {
            duration: 2000,
        });
    }

    reloadPage() {
        window.location.reload();
    }

}
