import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';

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
              private router: Router) {

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

    this.authService.login(this.loginForm.controls.username.value, this.loginForm.controls.password.value)
        .pipe(first())
        .subscribe(
            data => {
                window.alert('Erfolgreich angemeldet.');
            },
            error => {
                console.log(error);
            });

    this.router.navigate(['/dashboard']);
}


logout() {
    this.authService.logout();
}

}
