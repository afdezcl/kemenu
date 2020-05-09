import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { Router } from "@angular/router";
import { LocalStorageService } from "../../services/localStorage/localStorage.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private _localStorage: LocalStorageService, private router: Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler) {

        if (req.headers.get('NoAuth')) {
            return next.handle(req.clone());
        } else {

            const clonedreq = req.clone({
                headers: req.headers.set("Authorization", "Bearer " + this._localStorage.getToken())
            });
            return next.handle(clonedreq).pipe(
                tap(
                    event => { },
                    err => {
                        if (err.error.auth == false) {
                            this.router.navigateByUrl('');
                        }
                    })
            );
        }


    }
}