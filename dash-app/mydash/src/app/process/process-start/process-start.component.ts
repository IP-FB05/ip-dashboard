import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';

import * as CamSDK from './../../../../bower_components/camunda-bpm-sdk-js/camunda-bpm-sdk.js';
import 'jquery';

var processDefinitionId: String;

@Component({
  selector: 'app-process-start',
  templateUrl: './process-start.component.html',
  styleUrls: ['./process-start.component.css']
})
export class ProcessStartComponent implements OnInit {  

  constructor(
    public thisDialogRef: MatDialogRef<ProcessStartComponent>,
    @Inject(MAT_DIALOG_DATA) data) { 

      processDefinitionId = data.processDefinitionId;
  };

  ngOnInit() {
    $formContainer = $('#start');
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
  apiUri: 'http://localhost:8080/engine-rest'
});

var taskService = new camClient.resource('task');

function showTask(results) {
      // load the the task form (getting the task ID from the tag attribute)
      loadTaskForm(processDefinitionId, function(err, camForm) {
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

            this.onCloseConfirm();
          });
        });

        camForm.containerElement.append($submitBtn);
      });

}

function loadTaskForm(processDefinitionId, callback) {
    var url = 'http://localhost:8080/camunda-invoice/forms/start-form.html';

    new CamSDK.Form({
      client: camClient,
      formUrl: url,
      processDefinitionId: processDefinitionId,
      containerElement: $formContainer,

      // continue the logic with the callback
      done: callback
    });
}
