package de.fhaachen.ipdashboard.model;

public class ProcessInstance {

    private String id;
    private String definitionId;

    public ProcessInstance(String id, String definitionId) {
        this.id = id;
        this.definitionId = definitionId;
    }

    public String getId() {
        return id;
    }
    
    public String getDefinitionId() {
    	return definitionId;
    }
}