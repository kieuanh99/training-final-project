import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private AUTH_API = 'http://localhost:8080/api/auth/';

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post(
      this.AUTH_API + 'signin',
      {
        usernameOrEmail: credentials.usernameOrEmail,
        password: credentials.password,
      },
      httpOptions
    );
  }

  register(user: any): Observable<any> {
    return this.http.post(
      this.AUTH_API + 'signup',
      {
        username: user.username,
        email: user.email,
        password: user.password,
      },
      httpOptions
    );
  }
}
