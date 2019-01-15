package de.fhaachen.deploywf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;

public class UploadBPMN implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// save tempFile
		File tempFile = new File(execution.getVariable("definitionName") + ".bpmn");
		FileValue retrievedTypedFileValueBPMN = execution.getProcessEngineServices().getRuntimeService()
				.getVariableTyped(execution.getId(), "bpmn");
		InputStream fileContentBPMN = retrievedTypedFileValueBPMN.getValue(); // a byte stream of the file contents
		byte[] buffer = new byte[fileContentBPMN.available()];
		OutputStream outStream = new FileOutputStream(tempFile);
		outStream.write(buffer);
		outStream.close();

		// send Post request
		HttpUriRequest postRequest = RequestBuilder.post("http://localhost:9090/uploadFile/")
				.addHeader("Authorization", "Basic ZGFzaGJvYXJkOmRhc2hib2FyZFBX")
				.setEntity(MultipartEntityBuilder.create().addBinaryBody("file", tempFile).build()).build();

		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(postRequest);

		// check response
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception("HTTP Fehler: " + response.getStatusLine().getStatusCode());
		}

		// set path
		String path = "http://localhost:9090/files/" + tempFile.getName();
		execution.setVariable("bpmnpath", path);

		// delete temp file
		tempFile.delete();
	}

}
