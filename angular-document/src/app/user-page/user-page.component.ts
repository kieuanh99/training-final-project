import { Component, OnInit } from '@angular/core';
import { DocService } from '../_services/doc.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
})
export class UserPageComponent implements OnInit {
  typeRole: number = 1;
  users: any[];
  isLoggedIn = false;
  showAdminPage = false;
  showUserPage = false;
  private roles: string[];

  name: string;
  p: number = 1;

  constructor(
    private docService: DocService,
    private tokenStorageService: TokenStorageService
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getUser();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.showUserPage = this.roles.includes('ROLE_USER');
      this.showAdminPage = this.roles.includes('ROLE_ADMIN');
    }
    this.docService.getListUser(this.typeRole).subscribe((data) => {
      this.users = data;
    });
  }

  search() {
    if (this.name != '') {
      this.users = this.users.filter((res) => {
        return res.username
          .toLocaleLowerCase()
          .match(this.name.toLocaleLowerCase());
      });
    } else if (this.name == '') {
      this.ngOnInit();
    }
  }
}
