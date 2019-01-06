package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import java.lang.System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controller.StorageFileNotFoundException;
import controller.StorageService;
import controller.DashboardService;
import controller.Document;

@Controller
public class UploadController {

    private final StorageService storageService;
    private final DashboardService ds;

    @Autowired
    public UploadController(StorageService storageService, DashboardService ds) {
        this.storageService = storageService;
        this.ds = ds;
    }

    /*
    @GetMapping("/listFiles")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(UploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }
    */

    @GetMapping("/listFiles")
	public ResponseEntity<List<String>> listUploadedFiles(Model model) {
		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(UploadController.class, "serveFile", fileName).build().toString())
				.collect(Collectors.toList());
 
		return ResponseEntity.ok().body(fileNames);
	}

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /*
    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
    */

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


    List<String> files = new ArrayList<String>();
 
	@PostMapping("/uploadFile")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storageService.store(file);
            files.add(file.getOriginalFilename());
            /*String pfad = "http://localhost:9090/files/"+file.getOriginalFilename();
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            Document doc = new Document (99, "Informatik", file.getOriginalFilename(), date, pfad );
            ds.addDocument(doc);*/
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
    }
    
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> handleFileDelete(@RequestParam String filename) {
        String message = "";
        try {
            storageService.deleteOne(filename);
            //message = "You successfully deleted " + filename + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to delete " + filename;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @DeleteMapping("/deleteFiles")
    public ResponseEntity<String> handleFileDeleteProcess(@RequestParam String filenameBPMN, @RequestParam String filenameWAR) {
        String message = "";
        try {
            storageService.deleteOne(filenameBPMN);
            storageService.deleteOne(filenameWAR);
            //message = "You successfully deleted " + filename + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to delete " + filenameBPMN + " and " + filenameWAR ;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @DeleteMapping("/deleteBPMN")
    public ResponseEntity<String> handleFileDeleteBPMN(@RequestParam String filenameBPMN) {
        String message = "";
        try {
            storageService.deleteOne(filenameBPMN);
            //message = "You successfully deleted " + filename + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to delete " + filenameBPMN;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
 
	

}