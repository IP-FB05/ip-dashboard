import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';

import * as CamSDK from './../../../bower_components/camunda-bpm-sdk-js/camunda-bpm-sdk.js';
import 'jquery';

// Import Models
import { ProcessInstance } from '../process/processInstance';
import { Tasks } from './tasks';

// Import Components
import { DoTaskComponent } from './do-task/do-task.component';

// Import Services
import { MyprocessesService } from './myprocesses.service';
import { AuthService } from '../login/auth/auth.service';

declare var CamSDK: any;

var $formContainer;

var camClient = new CamSDK.Client({
  mock: false,
  apiUri: 'http://ip-dash.ddnss.ch:8080/engine-rest'
});

var taskService = new camClient.resource('task');

var items;

function loadTasks() {
  // fetch the list of available tasks
  taskService.list({
    // filter?
  }, function (err, results) {
    if (err) {
      throw err;
    }
    showTasks(results);
  });
}

/*function showTasks(results) {
  // generate the HTML for the list of tasks
  var items = [];
  $.each(results._embedded.task, function (t, task) {
    items = items.concat([
      '<li data-task-id="', task.id, '">',
      task.name || task.id,
      task.description ? '<div class="description">' : '',
      task.description,
      task.description ? '</div>' : '',
      '</li>'
    ]);
  });

  $('#tasks')
    // add the HTML to the list
    .html(items.join(''))

    // attach click events to the task list items
    .find('> li').click(function () {

      // load the the task form (getting the task ID from the tag attribute)
      loadTaskForm($(this).attr('data-task-id'), function(err, camForm) {
        if (err) {
          throw err;
        }

        var $submitBtn = $('<button type="submit">Complete</button>').click(function () {
          camForm.submit(function (err) {
            if (err) {
              throw err;
            }

            // clear the form
            $formContainer.html('');

            loadTasks();
          });
        });

        camForm.containerElement.append($submitBtn);
      });
    });
}


function loadTaskForm(taskId, callback) {
  // loads the task form using the task ID provided
  taskService.form(taskId, function(err, taskFormInfo) {
    var url = "http://ip-dash.ddnss.ch:8080" + taskFormInfo.key.replace('embedded:app:', taskFormInfo.contextPath + '/');

    new CamSDK.Form({
      client: camClient,
      formUrl: url,
      taskId: taskId,
      containerElement: $formContainer,

      // continue the logic with the callback
      done: callback
    });
  });
}*/

function showTasks(results) {
  // generate the HTML for the list of tasks
  $.each(results._embedded.task, function (t, task) {
    items.push( {id : task.id, processDefinitionName : '', state : '', startTime : '', startUserId : ''} );
  });
}


@Component({
  selector: 'app-myprocesses',
  templateUrl: './myprocesses.component.html',
  styleUrls: ['./myprocesses.component.css']
})
export class MyprocessesComponent implements OnInit {

  public instances: ProcessInstance[];
  public tasks: Tasks[];
  
  constructor(private myprocessService: MyprocessesService, public dialog: MatDialog, private authService: AuthService) { }

  ngOnInit() {
    $formContainer = $('.column.right');
    items = [];
    loadTasks();
    this.getInstances();
    this.getTasks();
  }

  getInstances(): void {
    this.myprocessService.getProcessInstances()
      .subscribe(instances => this.instances = instances);
  }

  getTasks(): void {
    this.myprocessService.getTasks()
      .subscribe(tasks => this.tasks = tasks);
  }

  doTask(instance: ProcessInstance) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      instance: instance.id
    };

    let dialogRef = this.dialog.open(DoTaskComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
    });

  }

}
