import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as toastr from 'toastr';
import {AuthService} from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hasError = false;
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {

  }

  submit() {
    const value = this.loginForm.value;
    if (this.loginForm.valid) {
      this.authService.authenticate(value.email, value.password).subscribe(() => {
        toastr.info(`User ${value.email} logged in`);
      }, () => {
        toastr.error(`Something went wrong`);
      });
    } else {
      this.hasError = true;
      toastr.error('Login invalid');
    }
  }
}
