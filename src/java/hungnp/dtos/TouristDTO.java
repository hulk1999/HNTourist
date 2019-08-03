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
public class TouristDTO implements Serializable{

	private int bookingID;
	private int touristID;
	private String tourCode; // for temporary while booking
	private String name;
	private String phone;
	private String address;
	private String gender;
	private String country;
	private String passport;
	
	public TouristDTO() {
	}

	public TouristDTO(int bookingID, int touristID, String tourCode, String name, String phone, String address, String gender, String country, String passport) {
		this.bookingID = bookingID;
		this.touristID = touristID;
		this.tourCode = tourCode;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.country = country;
		this.passport = passport;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public int getTouristID() {
		return touristID;
	}

	public void setTouristID(int touristID) {
		this.touristID = touristID;
	}

	public String getTourCode() {
		return tourCode;
	}

	public void setTourCode(String tourCode) {
		this.tourCode = tourCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}
	
}
