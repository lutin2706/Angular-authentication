import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.authService.connectedUser.subscribe(
      user => {
        if (user != null) {
          this.router.navigate([''])
        }
      }
    )
  }

  public onSubmit(loginForm: NgForm): void {
    if (loginForm.valid) {
      this.authService.login(loginForm.value);
    }
  }
}
