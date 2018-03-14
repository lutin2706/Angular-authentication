import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {AdminService} from "../../services/admin.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user: User;
  users: User[];

  constructor(private adminService: AdminService, private authService: AuthService) { }

  ngOnInit() {

    this.authService.connectedUser.subscribe(
      user => {
        this.user = user;
        this.loadUsers();
      },
          err => this.user = null
    );
  }

  private loadUsers(): void {
    this.adminService.getUsers().subscribe(
      users => this.users = users
    );
  }

  get isAdmin(): boolean {
    return this.user != null && this.user.authorities.filter(a => a.authority === 'ROLE_ADMIN').length > 0;
  }
}
