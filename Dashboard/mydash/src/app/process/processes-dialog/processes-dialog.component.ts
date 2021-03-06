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

var camClient;

var taskService;

function showTask(results) {
      // TODO
      loadTaskForm("prozessbereitstellung:3:483884a4-232b-11e9-a84a-00059a3c7a00", function(err, camForm) {
        if (err) {
          throw err;
        }

        var $submitBtn = $('<button type="submit" style="margin-top:10px;">Prozess deployen</button>').click(function () {
          camForm.submit(function (err, result) {
            if (err) {
              throw err;
            }

            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "http://localhost:9090/processInstanceAdd", false);
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
    var url = "http://localhost:8080" + taskFormInfo.key.replace('embedded:app:', taskFormInfo.contextPath + '/');

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
