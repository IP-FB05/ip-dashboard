package database;

public class RegisteredPruefungModel {
	public int id;
	public String name;
	public String email;
	public int pruefungsnr;
	
	public RegisteredPruefungModel(int id, String name, String email, int pruefungsnr) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.pruefungsnr = pruefungsnr;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getPruefungsnr() {
		return pruefungsnr;
	}
	
}
