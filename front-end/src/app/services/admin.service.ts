import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from "../models/user";

@Injectable()
export class AdminService {

  constructor(private http: HttpClient) {

  }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/admin/users');
  }
}
