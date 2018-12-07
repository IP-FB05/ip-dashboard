package database;

public class RegisteredPruefungModel {
	public int id;
	public String name;
	public Double note;
	public String email;
	public int pruefungsnr;
	
	public RegisteredPruefungModel(int id, String name, Double note,String email, int pruefungsnr) {
		this.id = id;
		this.name = name;
		this.note = note;
		this.email = email;
		this.pruefungsnr = pruefungsnr;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Double getNote() {
		return note;
	}

	public String getEmail() {
		return email;
	}

	public int getPruefungsnr() {
		return pruefungsnr;
	}
	
}
