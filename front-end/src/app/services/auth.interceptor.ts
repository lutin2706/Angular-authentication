import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let token = localStorage.getItem('token');

    if (token != null) {
      let authReq = req.clone({
        headers: req.headers.set('Authorization', "Bearer " + token)});  // On ne peut pas modifier une requête qui passe => obligation de la cloner
      return next.handle(authReq);
    }

    return next.handle(req);;
  }

}
