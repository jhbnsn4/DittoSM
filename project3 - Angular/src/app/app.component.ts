import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  populateTable() {
    console.log("hey")
  }

 

  filterTerm!: string 

  userRecords = [{
      "id": 1,
      "name": "Ryan Moss",
      "username": "Ryan",
      "email": "email@email"
    },
    {
      "id": 2,
      "name": "LJ",
      "username": "LJ-Ditto",
      "email": "email@email"
    },
    {
      "id": 3,
      "name": "Michael Kaefer",
      "username": "Mike",
      "email": "email@email"
    },
    {
      "id": 4,
      "name": "John Benson",
      "username": "John",
      "email": "email@email"
    }

  ]

  searchUserInput(){
    console.log("logout")
  }
   



}