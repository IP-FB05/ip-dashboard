package de.fhaachen.ipserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.fhaachen.ipserver.repository.DocumentRepository;
import de.fhaachen.ipserver.model.Document;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/")
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping(path = "/documents")
    public @ResponseBody Iterable<Document> getAllDocuments() {
        // System.out.println("Get all Systems...");
        return documentRepository.findAll();
    }

    @GetMapping(path = "/documents/{id}")
    public @ResponseBody Document getOneDocument(@RequestParam int id) {
        // System.out.println("Get System with ID: "+id);
        return documentRepository.findById(id).get();
    }

    @PostMapping(path = "/documentAdd")
    public @ResponseBody String addNewDocument(@RequestParam String name, @RequestParam String link,
            @RequestParam String lastChanged, @RequestParam int categoryID ) {

        Document n = new Document();
        n.setName(name);
        n.setLink(link);
        n.setLastChanged(lastChanged);
        n.setCategoryId(categoryID);
        documentRepository.save(n);
        return "Added succesfully";
    }

    @DeleteMapping(path = "/documentDelete")
    public @ResponseBody String deleteSystem(@RequestParam int id) {
        if (documentRepository.existsById(id)) {
            documentRepository.deleteById(id);
            return "System deleted.";
        } else
            return "System not found...";
    }

    /*
    @PutMapping(path = "/edit")
    public String editSystem(@RequestParam int id) {

        if (documentRepository.existsById(id)) {
            Document document = documentRepository.findById(id).get();
            document.setName("BEARBEITET");
            documentRepository.save(document);
            return "System succesfully edited.";
        } else
            return "System not found...";
    }
    */

}