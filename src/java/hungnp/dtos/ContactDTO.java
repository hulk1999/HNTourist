/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.dtos;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class ContactDTO implements Serializable{

	private int ID;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String message;
	private String sentTime;
	private boolean processed;
	
	public ContactDTO() {
	}

	public ContactDTO(int ID, String name, String email, String phone, String address, String message, String sentTime, boolean processed) {
		this.ID = ID;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.message = message;
		this.sentTime = sentTime;
		this.processed = processed;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSentTime() {
		return sentTime;
	}

	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
}
