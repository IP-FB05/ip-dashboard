import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

import * as CamSDK from './../../../../bower_components/camunda-bpm-sdk-js/camunda-bpm-sdk.js';
import 'jquery';

import { AuthorizationService } from '../../login/auth/authorization.service';

@Component({
  selector: 'app-processes-dialog',
  templateUrl: './processes-dialog.component.html',
  styleUrls: ['./processes-dialog.component.css']
})
export class ProcessesDialogComponent implements OnInit {

  constructor(
    private authorizationService: AuthorizationService,
    public thisDialogRef: MatDialogRef<ProcessesDialogComponent>) {
  }

  ngOnInit() {
    $formContainer = $('#deploy');
    camClient = new CamSDK.Client({
      mock: false,
      apiUri: 'http://localhost:8080/engine-rest',
      headers: {
        "Accept": "application/json",
        "Authorization": 'Basic ' + this.authorizationService.getAuthData(),
        "Content-Type": "application/json"
      }
    });
    taskService = new camClient.resource('process-definition');
    showTask('');
  }

  onCloseConfirm() {
    this.thisDialogRef.close('Confirm');
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }
}

declare var CamSDK: any;

var $formContainer;

var camClient = new CamSDK.Client({
  mock: false,
  apiUri: 'http://ip-dash.ddnss.ch:8080/engine-rest'
});

var taskService;

function showTask(results) {
      // load the the task form (getting the task ID from the tag attribute)
      // TODO
      loadTaskForm("prozessbereitstellung:5:6fc664f2-2194-11e9-8c43-b4b686e9bbd5", function(err, camForm) {
        if (err) {
          throw err;
        }

        var $submitBtn = $('<button type="submit">Prozess deployen</button>').click(function () {
          camForm.submit(function (err, result) {
            if (err) {
              throw err;
            }

            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "http://ip-dash.ddnss.ch:9090/processInstanceAdd", false);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Authorization", "Basic " + btoa('dashboard:dashboardPW'))
            xhttp.send(JSON.stringify({id : result.id, definitionId : result.definitionId}));

            // clear the form
            $formContainer.html('');

            location.reload();
          });
        });

        camForm.containerElement.append($submitBtn);
      });

}

function loadTaskForm(processDefinitionId, callback) {
  // loads the task form using the task ID provided
  taskService.startForm({ "id" :processDefinitionId }, function(err, taskFormInfo) {
    var url = "http://ip-dash.ddnss.ch:8080" + taskFormInfo.key.replace('embedded:app:', taskFormInfo.contextPath + '/');

    new CamSDK.Form({
      client: camClient,
      formUrl: url,
      processDefinitionId: processDefinitionId,
      containerElement: $formContainer,

      // continue the logic with the callback
      done: callback
    });
  });
}
