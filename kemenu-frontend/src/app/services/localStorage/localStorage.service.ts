import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class LocalStorageService {

  constructor() { }

  setToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  setUserId(id: string) {
    localStorage.setItem('id', id);
  }

  getUserId() {
    return localStorage.getItem('id');
  }

  deleteToken() {
    localStorage.removeItem('token');
  }

  isLoggedIn() {
    return !Object.is(this.getToken(), null);
  }
}
