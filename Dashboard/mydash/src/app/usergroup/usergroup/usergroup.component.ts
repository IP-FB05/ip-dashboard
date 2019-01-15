import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';


// Import Models 
import { Usergroup } from '../usergroup';

// Import Components

// Import Services
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


