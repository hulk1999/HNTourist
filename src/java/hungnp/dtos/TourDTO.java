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
public class TourDTO implements Serializable{

	private String code;
	private String startDate;
	private int price;
	private int priceForChildren;
	private int priceForBaby;
	private int discount;
	private int totalSeats;
	private int seatsBooked;
	private int tourArticleID;
	
	public TourDTO() {
	}

	public TourDTO(String code, String startDate, int price, int priceForChildren, int priceForBaby, int discount, int totalSeats, int seatsBooked, int tourArticleID) {
		this.code = code;
		this.startDate = startDate;
		this.price = price;
		this.priceForChildren = priceForChildren;
		this.priceForBaby = priceForBaby;
		this.discount = discount;
		this.totalSeats = totalSeats;
		this.seatsBooked = seatsBooked;
		this.tourArticleID = tourArticleID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPriceForChildren() {
		return priceForChildren;
	}

	public void setPriceForChildren(int priceForChildren) {
		this.priceForChildren = priceForChildren;
	}

	public int getPriceForBaby() {
		return priceForBaby;
	}

	public void setPriceForBaby(int priceForBaby) {
		this.priceForBaby = priceForBaby;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public int getTourArticleID() {
		return tourArticleID;
	}

	public void setTourArticleID(int tourArticleID) {
		this.tourArticleID = tourArticleID;
	}
	
}
