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

import de.fhaachen.ipserver.repository.SystemRepository;
import de.fhaachen.ipserver.model.System;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/")
public class SystemController {
    @Autowired
    private SystemRepository systemRepository;

    @GetMapping(path = "/systems")
    public @ResponseBody Iterable<System> getAllSystems() {
        // System.out.println("Get all Systems...");
        return systemRepository.findAll();
    }

    @GetMapping(path = "/systems/{id}")
    public @ResponseBody System getSystem(@RequestParam int id) {
        // System.out.println("Get System with ID: "+id);
        return systemRepository.findById(id).get();
    }

    @PostMapping(path = "/systemAdd")
    public @ResponseBody String addNewSystem(@RequestParam String name, @RequestParam String description,
            @RequestParam String link) {

        System n = new System();
        n.setName(name);
        n.setDescription(description);
        n.setLink(link);
        systemRepository.save(n);
        return "Added succesfully";
    }

    @DeleteMapping(path = "/systemDelete")
    public @ResponseBody String deleteSystem(@RequestParam int id) {
        if (systemRepository.existsById(id)) {
            systemRepository.deleteById(id);
            return "System deleted.";
        } else
            return "System not found...";
    }

    /*
    @PutMapping(path = "/edit")
    public String editSystem(@RequestParam int id) {

        if (systemRepository.existsById(id)) {
            System system = systemRepository.findById(id).get();
            system.setName("BEARBEITET");
            systemRepository.save(system);
            return "System succesfully edited.";
        } else
            return "System not found...";
    }
    */

}