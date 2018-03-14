import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  public connectedUser: User;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {

    this.authService.connectedUser.subscribe(
      user => this.connectedUser = user,
      err => this.connectedUser = null
    )
  }

  public logout(): void {
    this.authService.logout();
  }
}
