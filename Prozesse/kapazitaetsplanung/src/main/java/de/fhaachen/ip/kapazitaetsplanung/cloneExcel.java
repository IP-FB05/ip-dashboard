package de.fhaachen.ip.kapazitaetsplanung;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.*;


public class cloneExcel implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        try{

            String path = execution.getVariable("excelPath").toString();
            String dest = execution.getVariable("excelDest").toString();
            File excelFile = new File(path);
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            FileOutputStream fileOut = new FileOutputStream(dest);
            workbook.write(fileOut);
            fileOut.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}