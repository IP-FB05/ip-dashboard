import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usergroup } from '../usergroup';
import { UsergroupService } from '../usergroup.service'


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
};

@Component({
  selector: 'app-usergroup',
  templateUrl: './usergroup.component.html',
  styleUrls: ['./usergroup.component.css']
})
export class UsergroupComponent implements OnInit {

  constructor(private usergroupService: UsergroupService) {
  }

  usergroups: Usergroup[];


  ngOnInit() {
    this.usergroupService.getUsergroups().subscribe(usergroup => this.usergroups = usergroup);
    console.log(this.usergroups);
  }


}


