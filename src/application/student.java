package application;

import javafx.beans.property.StringProperty;

public class student {
    String name, password, id , course, dob,regd;
    
    

	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getRegd() {
		return regd;
	}
	public void setRegd(String regd) {
		this.regd = regd;
	}



	public student(String id,String name, String password, String dob, String regd ,String course) {
		this.id = id;
		this.name = name;
		this.password = password;	
		this.dob = dob;
		this.regd = regd;
		this.course = course;
	}

   
}
