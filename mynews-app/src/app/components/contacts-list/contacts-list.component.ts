import { Component, OnInit, Input } from '@angular/core';
import { User } from '../../classes/user';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.css'],
  providers: [UserService]
})
export class ContactsListComponent implements OnInit {
  @Input()
  public contacts: User[];

  constructor(
    private userService: UserService
  ) { }

  ngOnInit() {
  }

}
