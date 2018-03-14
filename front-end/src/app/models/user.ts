import {Authority} from "./authority";

export class User {

  constructor(public username: string,
              public password: string,
              public authorities?: Authority[]) {
  }
}
