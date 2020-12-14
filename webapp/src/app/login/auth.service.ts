import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {JwtResponse} from './jwt.response';
import {map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(private httpClient: HttpClient) {
  }

  authenticate(email: string, password: string): Observable<string> {
    return this.httpClient.post('/api/paymybuddy/login', {email, password}).pipe(
      map((jwtResponse: JwtResponse) => jwtResponse.accessToken),
      tap(token => localStorage.setItem('token', token)),
    );
  }
}
