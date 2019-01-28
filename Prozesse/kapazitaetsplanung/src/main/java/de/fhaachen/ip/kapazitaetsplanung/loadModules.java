package de.fhaachen.ip.kapazitaetsplanung;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class loadModules implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        //Prof Name laden
        String prof = execution.getVariable("prof_name").toString();

        //Excel spalte zuordnen
        int row = 7; //Erste Zeile nach dem Header der Exceldatei
        try {
            //Excel einlesen
            String path = execution.getVariable("excelPath").toString();
            File excelFile = new File(path);
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(1);

            //Zeile mit Prof. finden
            boolean next = true;
            boolean found = false;
            int breakCounter = 0;
            do {
                String row_prof = sheet.getRow(row - 1).getCell(0).toString();
                //Ist die Zeile Leer, Zur nächsten springen
                if (row_prof.equals("")) {
                    row = row + 1;
                    breakCounter = breakCounter + 1;

                //Wenn richtiger Prof gefunden
                } else if (row_prof.equals(prof)) {
                    next = false;
                    found = true;

                //Wenn falscher Prof gefunden
                } else if (!row_prof.equals(prof)) {
                    row = row + 15;	//15 zeilen skippen, da inerhalb keine Profs drin sein können (für bessere Performance)
                    breakCounter = 0;

                //Wenn seit längerem kein Prof mehr gefunden, beende suche
                } else if (breakCounter >= 40) {
                    next = false;
                    found = false;
                }
            } while (next == true);

            
            //Wenn Prof gefunden, schreibe alle Module in eine JSON prozessvariable,
            //zur späteren ausgabe in einem HTML form
            if(found == true){
                int modulCount = 0;
                JSONObject jsonMain = new JSONObject();
                String row_modul = sheet.getRow(row - 1 + modulCount).getCell(1).toString();
                do {
                    //execution.setVariable(prof+modulCount,row_modul);
                    HashMap map = new HashMap();

                    map.put("ModulName", row_modul);
                    
                    //Auslesen aller Hörer
                    ArrayList<String> arrHoerer = new ArrayList<>();
                    for(int j = 0; j < 13; j++) {	//insgesammt 13 Module vorhanden. momentan noch Hardcoded
                        //Wenn zuhörer vorhanden
                        if(!sheet.getRow(row - 1 + modulCount).getCell(j+2).toString().equals("")) {
                            arrHoerer.add(sheet.getRow(1).getCell(j + 2).toString());
                        }
                    }
                    map.put("Hoerer", arrHoerer.toString());

                  //Auslesen aller Mitarbeiter
                    ArrayList<String> arrMitarbeiter = new ArrayList<>();
                    for(int j = 0; j < 23; j++) {	//insgesammt 13 Mitarbeiter vorhanden. momentan noch Hardcoded

                        String uebung = sheet.getRow(row - 1 + modulCount).getCell(j*2+40).toString();
                        String praktikum = sheet.getRow(row - 1 + modulCount).getCell(j*2+41).toString();

                        if(!uebung.equals("") || !praktikum.equals("") ) {
                            String Ma = sheet.getRow(4).getCell(j*2+40).toString();
                            arrMitarbeiter.add(Ma+"|"+uebung+"|"+praktikum);
                        }
                    }
                    map.put("Mitarbiter", arrMitarbeiter.toString());
                    
                	//Hinzufügen weiterer Metadaten
                    map.put("Wahlfach", sheet.getRow(row - 1 + modulCount).getCell(15).toString());
                    map.put("Semester", sheet.getRow(row - 1 + modulCount).getCell(16).getRawValue());
                    map.put("Jahreszeit", sheet.getRow(row - 1 + modulCount).getCell(17).getRawValue());
                    
                    map.put("AV", sheet.getRow(row - 1 + modulCount).getCell(19).getNumericCellValue());
                    map.put("AU", sheet.getRow(row - 1 + modulCount).getCell(20).getNumericCellValue());
                    map.put("AP", sheet.getRow(row - 1 + modulCount).getCell(21).getNumericCellValue());
                    
                    map.put("NichtAnbieten", sheet.getRow(row - 1 + modulCount).getCell(22).getRawValue());

                    map.put("BU", sheet.getRow(row - 1 + modulCount).getCell(24).getNumericCellValue());
                    map.put("BP", sheet.getRow(row - 1 + modulCount).getCell(26).getNumericCellValue());

                    map.put("CP", sheet.getRow(row - 1 + modulCount).getCell(28).getNumericCellValue());
                    map.put("CS", sheet.getRow(row - 1 + modulCount).getCell(30).getNumericCellValue());

                    map.put("DU", sheet.getRow(row - 1 + modulCount).getCell(34).getNumericCellValue());
                    map.put("DP", sheet.getRow(row - 1 + modulCount).getCell(35).getNumericCellValue());

                    // Map als JSON exportierten
                    JSONObject json = new JSONObject(map);
                    jsonMain.put(Integer.toString(modulCount),json.toString());

                    modulCount = modulCount +1;
                    row_modul = sheet.getRow(row - 1 + modulCount).getCell(1).toString();

                } while(!row_modul.equals(""));
                //JSON aller module
                execution.setVariableLocal("loadedModules", Variables.objectValue(jsonMain.toString()).serializationDataFormat("application/json").create());
                //Geladene Module überarbeiten NICHT überspringen
                execution.setVariableLocal("skip",false);
                //für Zählvariablen
                execution.setVariableLocal("modulCount",modulCount);
                execution.setVariableLocal("modulCurrent",0);
                execution.setVariableLocal("profExcelRow",row);
            }

            else{
            	//Geladene Module überarbeiten NICHT überspringen
                execution.setVariableLocal("skip",true);
                //für Zählvariablen
                execution.setVariableLocal("modulCount",0);
                execution.setVariableLocal("modulCurrent",0);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
