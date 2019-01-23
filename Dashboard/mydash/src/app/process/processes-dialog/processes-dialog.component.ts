import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

import * as CamSDK from './../../../../bower_components/camunda-bpm-sdk-js/camunda-bpm-sdk.js';
import 'jquery';

@Component({
  selector: 'app-processes-dialog',
  templateUrl: './processes-dialog.component.html',
  styleUrls: ['./processes-dialog.component.css']
})
export class ProcessesDialogComponent implements OnInit {

  constructor(
    public thisDialogRef: MatDialogRef<ProcessesDialogComponent>) {
  }

  ngOnInit() {
    $formContainer = $('#deploy');
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
  apiUri: 'http://149.201.176.231:8080/engine-rest'
});

var taskService = new camClient.resource('process-definition');

function showTask(results) {
      // load the the task form (getting the task ID from the tag attribute)
      loadTaskForm("prozessbereitstellung:1:9b929be2-1d70-11e9-88fa-b4b686e9bbd5", function(err, camForm) {
        if (err) {
          throw err;
        }

        var $submitBtn = $('<button type="submit">Prozess deployen</button>').click(function () {
          camForm.submit(function (err, result) {
            if (err) {
              throw err;
            }

            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "http://149.201.176.231:9090/processInstanceAdd", false);
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
    var url = "http://149.201.176.231:8080" + taskFormInfo.key.replace('embedded:app:', taskFormInfo.contextPath + '/');

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
