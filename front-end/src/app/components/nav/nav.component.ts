import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  public user: User;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.connectedUser.subscribe(
      user => this.user = user,
      err => this.user = null
    );
  }

  public logout(): void {
    this.authService.logout();
  }
}
