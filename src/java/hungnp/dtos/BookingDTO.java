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
public class BookingDTO implements Serializable{

	private int id;
	private String tourCode;
	private String username;
	private int adultNumber;
	private int childNumber;
	private int babyNumber;
	private String paymentMethod;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String contactAddress;
	private String status;
	
	public BookingDTO() {
	}

	public BookingDTO(int id, String tourCode, String username, int adultNumber, int childNumber, int babyNumber, String paymentMethod, String contactName, String contactPhone, String contactEmail, String contactAddress, String status) {
		this.id = id;
		this.tourCode = tourCode;
		this.username = username;
		this.adultNumber = adultNumber;
		this.childNumber = childNumber;
		this.babyNumber = babyNumber;
		this.paymentMethod = paymentMethod;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
		this.contactAddress = contactAddress;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTourCode() {
		return tourCode;
	}

	public void setTourCode(String tourCode) {
		this.tourCode = tourCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAdultNumber() {
		return adultNumber;
	}

	public void setAdultNumber(int adultNumber) {
		this.adultNumber = adultNumber;
	}

	public int getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(int childNumber) {
		this.childNumber = childNumber;
	}

	public int getBabyNumber() {
		return babyNumber;
	}

	public void setBabyNumber(int babyNumber) {
		this.babyNumber = babyNumber;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
