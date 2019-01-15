package database;

public class RegisteredModulesModel {
	public int id;
	public String module;
	
	public RegisteredModulesModel(int id, String module) {
		this.id = id;
		this.module = module;
	}

	public int getId() {
		return id;
	}

	public String getModule() {
		return module;
	}
	
}
