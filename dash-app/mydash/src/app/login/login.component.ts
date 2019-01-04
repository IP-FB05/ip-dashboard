import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              public snackBar: MatSnackBar) {

    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required] 
    });

   }

  ngOnInit() {
  }

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
            }); */

    this.router.navigate(['/dashboard']);
}


logout() {
    this.authService.logout();
}

openSnackBar(text: string) {
    this.snackBar.open(text, '', {
      duration: 2000,
    });
  }

}
