import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

import * as CamSDK from './../../../../bower_components/camunda-bpm-sdk-js/camunda-bpm-sdk.js';
import 'jquery';


// Import Models
import { ProcessInstance } from 'src/app/process/processInstance.js';

// Import Components

// Import Services
import { DoTaskService } from './do-task.service';
import { AuthService } from '../../login/auth/auth.service';
import { AuthorizationService } from '../../login/auth/authorization.service';

var taskId: String;
var instance: String

@Component({
  selector: 'app-do-task',
  templateUrl: './do-task.component.html',
  styleUrls: ['./do-task.component.css']
})
export class DoTaskComponent implements OnInit {

  constructor(
    private authorizationService: AuthorizationService,
    private doTaskService: DoTaskService,
    public thisDialogRef: MatDialogRef<DoTaskComponent>,
    @Inject(MAT_DIALOG_DATA) data) { 
      instance = data.instance;
  };

  ngOnInit() {
    $formContainer = $('#task');
    camClient = new CamSDK.Client({
      mock: false,
      apiUri: 'http://localhost:8080/engine-rest',
      headers: {
        "Accept": "application/json",
        "Authorization": 'Basic ' + this.authorizationService.getAuthData(),
        "Content-Type": "application/json"
      }
    });
    
    var taskService = new camClient.resource('task')
    loadTasks();
  }

  onCloseConfirm() {
    this.thisDialogRef.close('Confirm');
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }

  getTaskId(instance: String) {
    this.doTaskService.getTaskId(instance)
      .subscribe(data => taskId = data.id);
  }
}

declare var CamSDK: any;

var $formContainer;

var camClient;

var taskService;

function loadTasks() {
  // fetch the list of available tasks
  taskService.list({
    "processInstanceId" : instance
  }, function (err, results) {
    if (err) {
      throw err;
    }
    showTasks(results);
  });
}

function showTasks(results) {
  // generate the HTML for the list of tasks
  var items = results[0].id;
  
      // load the the task form (getting the task ID from the tag attribute)
      loadTaskForm(items, function(err, camForm) {
        if (err) {
          throw err;
        }

        var $submitBtn = $('<button type="submit">Task abschicken</button>').click(function () {
          camForm.submit(function (err) {
            if (err) {
              throw err;
            }

            // clear the form
            $formContainer.html('');
            location.reload();
          });
        });

        camForm.containerElement.append($submitBtn);
      });
}


function loadTaskForm(taskId, callback) {
  // loads the task form using the task ID provided
  taskService.form(taskId, function(err, taskFormInfo) {
    var url = "http://localhost:8080" + taskFormInfo.key.replace('embedded:app:', taskFormInfo.contextPath + '/');

    new CamSDK.Form({
      client: camClient,
      formUrl: url,
      taskId: taskId,
      containerElement: $formContainer,

      // continue the logic with the callback
      done: callback
    });
  });
}

