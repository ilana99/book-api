import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../api.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.sass'
})
export class LoginComponent {

  response: any;
  
  constructor(private apiService: ApiService, private authService: AuthService) {}

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  onSubmit() {
    const username = this.loginForm.get('username')?.value;
    const password = this.loginForm.get('password')?.value;
    if (username && password) {
      this.apiService.login(username, password)
      .subscribe(response => {
        console.log(response);
        this.getCredentials(response);
      })
    }
  }

  getCredentials(response: any) {
    if (response && response.token && response.refreshToken) {
      const token = response.token;
      const refreshToken = response.refreshToken;
      localStorage.setItem('token', token);
      localStorage.setItem('refreshToken', refreshToken);
      this.authService.setAuthenticationStatus(true);
    } else {
      console.error('Invalid response format or response is undefined');
    }
  }

}