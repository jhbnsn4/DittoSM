import { Component, OnInit } from '@angular/core';
import { IUserAccount } from 'src/app/model/useraccount';
import { AjaxService } from 'src/app/service/ajax.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private ajaxService: AjaxService) { }

  ngOnInit(): void {
  }

  onSearchById(event: Event) {
    let response = this.ajaxService.getUserById(1).subscribe(
      data => {
        console.log(data);
      }
    );

  }
}
