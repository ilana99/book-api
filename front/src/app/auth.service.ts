// declare var localStorage: Storage;
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { 
    this.setAuthenticated()
  }

  isAuthenticated = false;

  getAuthenticationStatus(): boolean {
    return this.isAuthenticated;
  }

  setAuthenticationStatus(status: boolean): void {
    this.isAuthenticated = status;
  }

  setAuthenticated() {
    if (typeof localStorage !== 'undefined' && localStorage.getItem('token') && localStorage.getItem('refreshToken')) {
      this.setAuthenticationStatus(true);
    }
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    this.setAuthenticationStatus(false);
  }

 

}
