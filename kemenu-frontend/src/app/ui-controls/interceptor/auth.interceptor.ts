import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { catchError, switchMap, filter, take } from 'rxjs/operators';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { throwError, BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    
    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(
        private _authService: AuthenticationService,
        private router: Router
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler) {
        if (this._authService.getJwtToken()) {
            request = this.addToken(request, this._authService.getJwtToken());
        }

        return next.handle(request).pipe(catchError(error => {
            if (error instanceof HttpErrorResponse && error.status === 401) {
                return this.handle401Error(request, next);
            } else {
                return throwError(error);
            }
        }));
    }

    private addToken(request: HttpRequest<any>, token: string) {
        return request.clone({
            setHeaders: {
                'Authorization': `${token}`
            }
        });
    }


    private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
        if (!this.isRefreshing) {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);

            
            return this._authService
                    .refreshToken().pipe(
                    switchMap((token: any) => {
                        console.log("Request new token")
                        this.isRefreshing = false;
                        this.refreshTokenSubject.next(token);
                        return next.handle(this.addToken(request, token.jwt))
                    
                    }));

        } else {
            return this.refreshTokenSubject
                    .pipe(filter(token => token != null),
                          take(1),
                          switchMap(jwt => {
                          return next.handle(this.addToken(request, jwt));
                    }));
        }
    }


}