package de.fhaachen.ip.kapazitaetsplanung;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.*;


public class cloneExcel implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        try{
    		//Pfade für die Exceldateien einlesen
            String path = execution.getVariable("excelPath").toString();
            String dest = execution.getVariable("excelDest").toString();
            
            //Excel laden
            File excelFile = new File(path);
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            //Unter anderen Namen speichern
            FileOutputStream fileOut = new FileOutputStream(dest);
            workbook.write(fileOut);
            fileOut.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}