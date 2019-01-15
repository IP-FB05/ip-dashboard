package de.fhaachen.deploywf;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;

public class UploadBPMN implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String tmp = "http://localhost:9090/uploadFile/";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			tmp);
			
		File tempFile = new File(execution.getVariable("definitionName") + ".bpmn");
		
		postRequest.addHeader("accept", "application/json");
		postRequest.addHeader("Authorization", "Basic ZGFzaGJvYXJkOmRhc2hib2FyZFBX");
		
		FileValue retrievedTypedFileValueBPMN = execution.getProcessEngineServices().getRuntimeService().getVariableTyped(execution.getId(), "bpmn");
		InputStream fileContentBPMN = retrievedTypedFileValueBPMN.getValue(); // a byte stream of the file contents
		byte[] buffer = new byte[fileContentBPMN.available()];
		
		HttpParams params = new HttpParams();
		params.setParam("file", tempFile);
		
		postRequest.setParams(params);
		
		tempFile.write(buffer);
		
		HttpResponse response = httpClient.execute(postRequest);
		
		if (response.getStatusLine().getStatusCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

		String path = br.readLine();
		execution.setVariable("bpmnpath", path);
		
	}

}
