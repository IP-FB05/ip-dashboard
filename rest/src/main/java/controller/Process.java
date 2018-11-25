package controller;

public class Process {

    private int processID;
    private String name;
    private String description;
    private String pic;
    private String varFile;
    private String bpmn;
    private String added;

    public Process(int processID, String name, String description, String pic, String varFile, String bpmn, String added) {
        this.processID = processID;
        this.name = name;
        this.description = description;
        this.pic = pic;
        this.varFile = varFile;
        this.bpmn = bpmn;
        this.added = added;
    }

    public long getProcessID() {
        return processID;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public String getPic() {
    	return pic;
    }

    public String getVarFile() {
    	return varFile;
    }

    public String getBpmn() {
    	return bpmn;
    }

    public String getAdded() {
    	return added;
    }
}