/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.models;

import hungnp.database.HNConnection;
import hungnp.dtos.BookingDTO;
import hungnp.dtos.TouristDTO;
import hungnp.support.ListPack;
import hungnp.support.Parser;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BookingDAO implements Serializable{
	
	Connection conn;
	PreparedStatement preStm;
	ResultSet rs;

	public BookingDAO() {
	}
	
	private void closeConnection() throws Exception{
		if (rs != null) rs.close();
		if (preStm != null) preStm.close();
		if (conn != null) conn.close();
	}
	
	public boolean add(BookingDTO dto, List<TouristDTO> touristList) throws Exception{
		boolean success = false;
		try {
			
			String sql = "INSERT INTO Booking(ID, TourCode, Username, AdultNumber, ChildNumber, BabyNumber, PaymentMethod, ContactName, ContactPhone, ContactEmail, ContactAddress, Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, dto.getId());
			preStm.setString(2, dto.getTourCode());
			preStm.setString(3, dto.getUsername());
			preStm.setInt(4, dto.getAdultNumber());
			preStm.setInt(5, dto.getChildNumber());
			preStm.setInt(6, dto.getBabyNumber());
			preStm.setString(7, dto.getPaymentMethod().equals("transfer") ? "Chuyển khoản" : "Tiền mặt");
			preStm.setString(8, dto.getContactName());
			preStm.setString(9, dto.getContactPhone());
			preStm.setString(10, dto.getContactEmail());
			preStm.setString(11, dto.getContactAddress());
			preStm.setString(12, "Chưa thanh toán");
			success = preStm.executeUpdate() > 0;
			
			sql = "INSERT INTO Tourist(BookingID, TouristID, Name, Phone, Address, Gender, Country, Passport) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			preStm = conn.prepareStatement(sql);
			int count = 0;
			for (TouristDTO dtoIter : touristList){
				preStm.setInt(1, dto.getId());
				preStm.setInt(2, ++count);
				preStm.setString(3, dtoIter.getName());
				preStm.setString(4, dtoIter.getPhone());
				preStm.setString(5, dtoIter.getAddress());
				preStm.setString(6, dtoIter.getGender());
				preStm.setString(7, dtoIter.getCountry());
				preStm.setString(8, dtoIter.getPassport());
				success = success && preStm.executeUpdate() > 0;
			}
			
		} finally{
			closeConnection();
		}
		return success;
	}
	
	public int getLastID() throws Exception{
        int result = 0;
        try {
            String sql = "SELECT MAX(ID) AS MaxID FROM Booking";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            rs.next();
            if (rs.getString("MaxID") != null) result = Integer.parseInt(rs.getString("MaxID"));
        } finally{
            closeConnection();
        }
        return result;
    }
	
	public BookingDTO getBooking(int id) throws Exception{
		BookingDTO result = null;
		try {
			String sql = "SELECT ID, TourCode, Username, AdultNumber, ChildNumber, BabyNumber, PaymentMethod, ContactName, ContactPhone, ContactEmail, ContactAddress, Status FROM Booking " +
						 "WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, id);
			rs = preStm.executeQuery();
			if (rs.next()){
				String tourCode = rs.getString("TourCode");
				String username = rs.getString("Username");
				int adultNumber = rs.getInt("AdultNumber");
				int childNumber = rs.getInt("ChildNumber");
				int babyNumber = rs.getInt("BabyNumber");
				String paymentMethod = rs.getString("PaymentMethod");
				String contactName = rs.getString("ContactName");
				String contactPhone = rs.getString("ContactPhone");
				String contactEmail = rs.getString("ContactEmail");
				String contactAddress = rs.getString("ContactAddress");
				String status = rs.getString("Status");
				result = new BookingDTO(id, tourCode, username, adultNumber, childNumber, babyNumber, paymentMethod, contactName, contactPhone, contactEmail, contactAddress, status);
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public ListPack getBookingList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<BookingDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, TourCode, Username, AdultNumber, ChildNumber, BabyNumber, PaymentMethod, ContactName, Status FROM Booking " +
						 "ORDER BY ID DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int id = rs.getInt("ID");
				String tourCode = rs.getString("TourCode");
				String username = rs.getString("Username");
				int adultNumber = rs.getInt("AdultNumber");
				int childNumber = rs.getInt("ChildNumber");
				int babyNumber = rs.getInt("BabyNumber");
				String paymentMethod = rs.getString("PaymentMethod");
				String contactName = rs.getString("ContactName");
				String status = rs.getString("Status");
				list.add(new BookingDTO(id, tourCode, username, adultNumber, childNumber, babyNumber, paymentMethod, contactName, null, null, null, status));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getBookingList(String search, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<BookingDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, TourCode, Username, AdultNumber, ChildNumber, BabyNumber, PaymentMethod, ContactName, Status FROM Booking " +
						 "WHERE (TourCode LIKE ?) OR (ContactName LIKE ?) OR (Status LIKE ?)" +
						 "ORDER BY ID DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preStm.setString(1, "%" + search + "%");
			preStm.setString(2, "%" + search + "%");
			preStm.setString(3, "%" + search + "%");
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int id = rs.getInt("ID");
				String tourCode = rs.getString("TourCode");
				String username = rs.getString("Username");
				int adultNumber = rs.getInt("AdultNumber");
				int childNumber = rs.getInt("ChildNumber");
				int babyNumber = rs.getInt("BabyNumber");
				String paymentMethod = rs.getString("PaymentMethod");
				String contactName = rs.getString("ContactName");
				String status = rs.getString("Status");
				list.add(new BookingDTO(id, tourCode, username, adultNumber, childNumber, babyNumber, paymentMethod, contactName, null, null, null, status));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public boolean delete(int id) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM Booking WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, id);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	public boolean changeStatus(int id, String status) throws Exception{
		boolean success = false;
		try {
			String sql = "UPDATE Booking SET Status = ? WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, status);
			preStm.setInt(2, id);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	public List<TouristDTO> getTouristList(int id) throws Exception{
		List<TouristDTO> list = new ArrayList();
		try {
			String sql = "SELECT Name, Phone, Address, Gender, Country, Passport FROM Tourist " +
						 "WHERE BookingID = ? " +
						 "ORDER BY TouristID";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, id);
			rs = preStm.executeQuery();
			while (rs.next()){
				String name = rs.getString("Name");
				String phone = rs.getString("Phone");
				String address = rs.getString("Address");
				String gender = rs.getString("Gender");
				String country = rs.getString("Country");
				String passport = rs.getString("Passport");
				list.add(new TouristDTO(-1, -1, null, name, phone, address, gender, country, passport));
			}
		} finally{
			closeConnection();
		}
		return list;
	}
	
	public List<BookingDTO> getBookingListForAccount(String username) throws Exception{
		List<BookingDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, TourCode, AdultNumber, ChildNumber, BabyNumber, Status FROM Booking " +
						 "WHERE Username = ? " +
						 "ORDER BY ID DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, username);
			rs = preStm.executeQuery();
			while (rs.next()){
				int id = rs.getInt("ID");
				String tourCode = rs.getString("TourCode");
				int adultNumber = rs.getInt("AdultNumber");
				int childNumber = rs.getInt("ChildNumber");
				int babyNumber = rs.getInt("BabyNumber");
				String status = rs.getString("Status");
				list.add(new BookingDTO(id, tourCode, username, adultNumber, childNumber, babyNumber, null, null, null, null, null, status));
			}
		} finally{
			closeConnection();
		}
		return list;
	}
	
}
