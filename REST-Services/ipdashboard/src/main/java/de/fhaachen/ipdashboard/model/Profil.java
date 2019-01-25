package de.fhaachen.ipdashboard.model;

public class Profil {


    private String firstName;
    private String lastName;
    private String email;
    private Group[] groups;

    /**
     * @return the groups
     */
    public Group[] getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String toString() {
        return this.firstName + " " + this.lastName + " (" + this.email + ")";
    }

}