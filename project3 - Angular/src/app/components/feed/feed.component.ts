import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  
  @Input() userid: number=0;
  

  constructor() { }

  ngOnInit(): void {
    // console.log(this.userid);
  }

}
