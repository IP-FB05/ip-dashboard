package de.fhaachen.ip.kapazitaetsplanung;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;
import java.io.*;
import java.util.HashMap;

public class writeLoadedModules implements JavaDelegate {

    public void execute(DelegateExecution execution) {

    	//Zusätzliche Daten laden
        String prof =  execution.getVariable("prof_name").toString();
        int row =  Integer.parseInt(execution.getVariable("profExcelRow").toString());

        try{
        	//Exceldatei Laden
            String dest = execution.getVariable("excelDest").toString();
            int modulCount = Integer.parseInt(execution.getVariable("modulCount").toString());
            File excelFile = new File(dest);
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            fis.close();
            XSSFSheet sheet = workbook.getSheetAt(1);

            int modulCurrent = 0;

            do {
        		//JSON in richtiges format bringen
                String modulJSON = execution.getVariable(prof + (modulCurrent + 1)).toString();
                modulJSON = modulJSON.replace("{","").replace("}","");
                modulJSON = "{" + modulJSON.substring(1, modulJSON.length()-1) + "}";
                JSONObject modul = new JSONObject(modulJSON);

                //Fach Name in Exceldatei schreiben
                sheet.getRow(row - 1 + modulCurrent).getCell(1).setCellValue(modul.getString("FachName"));

                //Wahlmodul in Exceldatei schreiben
                boolean wahlmodul = modul.getBoolean("Wahlmodul");
                if(wahlmodul == true){
                    sheet.getRow(row - 1 + modulCurrent).getCell(15).setCellValue("x");
                }
                else{
                    sheet.getRow(row - 1 + modulCurrent).getCell(15).setCellValue("");
                }
                
                //In Exceldatei schreiben, ob das Modul dieses Semester angeboten wird
                boolean nichtAnbieten = modul.getBoolean("NichtAnbieten");
                if(nichtAnbieten == true){
                    sheet.getRow(row - 1 + modulCurrent).getCell(22).setCellValue("x");
                }
                else{
                    sheet.getRow(row - 1 + modulCurrent).getCell(22).setCellValue("");
                }

                //Weitere Daten in Exceldatei schreiben
                sheet.getRow(row - 1 + modulCurrent).getCell(16).setCellValue(modul.getString("Semester"));
                sheet.getRow(row - 1 + modulCurrent).getCell(17).setCellValue(modul.getString("SemesterArt"));

                sheet.getRow(row - 1 + modulCurrent).getCell(19).setCellValue(modul.getString("AV"));
                sheet.getRow(row - 1 + modulCurrent).getCell(20).setCellValue(modul.getString("AU"));
                sheet.getRow(row - 1 + modulCurrent).getCell(21).setCellValue(modul.getString("AP"));

                sheet.getRow(row - 1 + modulCurrent).getCell(24).setCellValue(modul.getString("BU"));
                sheet.getRow(row - 1 + modulCurrent).getCell(26).setCellValue(modul.getString("BP"));

                sheet.getRow(row - 1 + modulCurrent).getCell(28).setCellValue(modul.getString("CP"));
                sheet.getRow(row - 1 + modulCurrent).getCell(30).setCellValue(modul.getString("CS"));

                sheet.getRow(row - 1 + modulCurrent).getCell(34).setCellValue(modul.getString("DU"));
                sheet.getRow(row - 1 + modulCurrent).getCell(35).setCellValue(modul.getString("DP"));

            	//Hörer parsen und in Exceldatei schreiben
                String hoerer = modul.getString("Hoerer");
                String[] hoererArr = hoerer.split(",");
                for(int i = 0; i < 13; i++) {	//anzahl momentan hardcoded
                    String header = sheet.getRow(1).getCell(i + 2).getStringCellValue();
                    header = header.replaceAll("\\s","");
                    if(header.substring(0,4).equals("B-In") || header.substring(0,4).equals("B-SB")){
                        header = header.substring(0, header.length() - 1);
                    }

                    for(int j = 0; j < hoererArr.length; j++) {
                        if (header.equals(hoererArr[j].replaceAll("\\s+$","").trim())) {
                            sheet.getRow(row - 1 + modulCurrent).getCell(i + 2).setCellValue("x");

                            break;
                        }

                        else{
                            sheet.getRow(row - 1 + modulCurrent).getCell(i + 2).setCellValue("");
                        }

                    }
                }

                //Mitarbiter parsen und in Exceldatei schreiben
                String mitarbiter = modul.getString("Mitarbiter");
                String[] mitarbiterArr = mitarbiter.split(",");
                for(int i = 0; i < 23; i++) {	//anzahl momentan hardcoded

                    for(int j = 0; j < mitarbiterArr.length; j++) {


                        if (sheet.getRow(4).getCell(i*2+40).toString().equals(mitarbiterArr[j].split(":")[0])) {
                            sheet.getRow(row - 1 + modulCurrent).getCell(i*2+40).setCellValue(mitarbiterArr[j].split(":")[1]);
                            if(mitarbiterArr[j].split(":").length == 3){
                                sheet.getRow(row - 1 + modulCurrent).getCell(i*2+41).setCellValue(mitarbiterArr[j].split(":")[2]);
                            }
                            break;
                        }
                        else{
                            sheet.getRow(row - 1 + modulCurrent).getCell(i*2+40).setCellValue("");
                            sheet.getRow(row - 1 + modulCurrent).getCell(i*2+41).setCellValue("");
                        }

                    }
                }

                modulCurrent++;


            }while(modulCurrent < modulCount);

            FileOutputStream fos = new FileOutputStream(dest);
            workbook.write(fos);
            fos.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
