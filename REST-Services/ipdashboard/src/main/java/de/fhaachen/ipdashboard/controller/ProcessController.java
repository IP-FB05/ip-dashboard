package de.fhaachen.ipdashboard.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.fhaachen.ipdashboard.database.Dashboard;


import de.fhaachen.ipdashboard.model.Process;
import de.fhaachen.ipdashboard.model.ProcessInstance;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/")
public class ProcessController {

    // GET
    @RequestMapping(value = "/processes", method = RequestMethod.GET)
    public Process[] getProcesses(@RequestParam String role) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        Process[] result = dash.getProcesses(role);
        dash.close();
        return result;
    }

    // GET with ID
    @RequestMapping(value = "/process/{processID}", method = RequestMethod.GET)
    public Process getProcess(@PathVariable int processID) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        Process result = dash.getProcess(processID);
        dash.close();
        return result;
    }

    // ADD
    @RequestMapping(value = "/processAdd", method = RequestMethod.POST)
    @ResponseBody
    public boolean addProcess(@RequestBody Process input) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        boolean result = dash.addProcess(input);
        dash.close();
        return result;
    }

    // ADD ProcessInstance
    @RequestMapping(value = "/processInstanceAdd", method = RequestMethod.POST)
    @ResponseBody
    public boolean addProcessInstance(@RequestBody ProcessInstance input) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        boolean result = dash.addProcessInstance(input);
        dash.close();
        return result;
    }

    // DELETE
    @RequestMapping(value = "/processDelete/{processID}", method = RequestMethod.DELETE)
    public boolean deleteProcess(@PathVariable int processID) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        boolean result = dash.deleteProcess(processID);
        dash.close();
        return result;
    }

    @RequestMapping(value = "/getUserGroups", method = RequestMethod.GET)
    public String getUserGroupsFromProcess(@RequestParam int pid) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        String result = dash.getUserGroupsFromProcess(pid);
        dash.close();
        return result;
    }

    // ADD ProcessDeploy
    @RequestMapping(value = "/processDeploy", method = RequestMethod.POST)
    public int addProcessDeploy(@RequestParam("name") String name, @RequestParam("beschreibung") String beschreibung,
            @RequestParam("bpmn") String bpmn, @RequestParam("verbal") String verbal,
            @RequestParam("camunda_processID") String camunda_processID, @RequestParam("datum") String datum,
            @RequestParam("ersteller") String ersteller) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        int result = dash.addProcessDeploy(name, beschreibung, bpmn, verbal, camunda_processID, datum, ersteller);
        dash.close();
        return result;
    }

    // DELETE ProcessDeploy
    @RequestMapping(value = "/processDeploy/{dbID}", method = RequestMethod.DELETE)
    public boolean deleteProcessDeploy(@PathVariable Long dbID) throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        boolean result = dash.deleteProcessDeploy(dbID);
        dash.close();
        return result;
    }

    // PATCH ProcessDeploy
    @RequestMapping(value = "/processDeploy/{dbID}", method = RequestMethod.PATCH)
    public boolean patchProcessDeploy(@PathVariable Long dbID, @RequestParam("published") boolean published)
            throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        boolean result = dash.patchProcessDeploy(dbID, published);
        dash.close();
        return result;
    }

    // PATCH ProcessDeploy Usergroup
    @RequestMapping(value = "/processDeploy/{dbID}/usergroup/{usergroup}", method = RequestMethod.PATCH)
    public boolean patchProcessDeployUsergroup(@PathVariable Long dbID, @PathVariable String usergroup)
            throws SQLException, ClassNotFoundException {
        Dashboard dash = new Dashboard();
        boolean result = dash.patchProcessDeployUsergroup(dbID, usergroup);
        dash.close();
        return result;
    }

}
