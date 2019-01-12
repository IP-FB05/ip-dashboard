package de.fhaachen.deploywf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;

public class DeployProcess implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO besser über Tomcat manager API
		
		// war File holen
		FileValue retrievedTypedFileValueWAR = execution.getProcessEngineServices().getRuntimeService().getVariableTyped(execution.getId(), "war");
		InputStream fileContentWAR = retrievedTypedFileValueWAR.getValue(); // a byte stream of the file contents

		// Ziel Path bestimmen
		String catalinaHome = System.getProperty("catalina.base");
		String outputPath = catalinaHome + System.getProperty("file.separator") + "webapps";
		
		// File anlegen
		File f = new File(outputPath);
		f.createNewFile();
		OutputStream out = new FileOutputStream(f);

		// Fileinhalt byteweise rüberschreiben
		byte[] buf = new byte[1024];
		int len;
		while ((len = fileContentWAR.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		fileContentWAR.close();
		out.close();

	}

}
