/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.models;

import hungnp.database.HNConnection;
import hungnp.dtos.BookingDTO;
import hungnp.dtos.TourDTO;
import hungnp.support.ListPack;
import hungnp.support.Parser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class TourDAO {
	
	// add list
	// delete
	// edit
	// get attribute
	// get value
	// get list
	// ========================================================================================== //
	
	Connection conn;
	PreparedStatement preStm;
	ResultSet rs;
	
	private void closeConnection() throws Exception{
		if (rs != null) rs.close();
		if (preStm != null) preStm.close();
		if (conn != null) conn.close();
	}
	
	// add list
	// ========================================================================================== //
	
	public boolean addTours(int ID, String[] startDateList, int[] priceList, int[] priceForChildrenList, int[] priceForBabyList, int[] discountList, int[] totalSeatsList) throws Exception{
		boolean success = false;
		if (startDateList == null) return true;
		try {
			conn = HNConnection.getConnection();
			success = true;
			for (int i = 0; i <= startDateList.length - 1; i++){
				
				String startDateTourCodeFormat = Parser.stringToTourCodeDateFormat(startDateList[i]);
				int lastCodeNumber = 0;
				String sql = "SELECT MAX(Code) AS MaxCode FROM Tour WHERE Code LIKE ?";
				preStm = conn.prepareStatement(sql);
				preStm.setString(1, "HNT-" + startDateTourCodeFormat + "-" + "%");
				rs = preStm.executeQuery();
				rs.next();
				if (rs.getString("MaxCode") != null) lastCodeNumber = Integer.parseInt(rs.getString("MaxCode").substring(11));
				
				sql = "INSERT INTO Tour(Code, StartDate, Price, PriceForChildren, PriceForBaby, Discount, TotalSeats, SeatsBooked, TourArticleID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				preStm = conn.prepareStatement(sql);
				preStm.setString(1, "HNT-" + startDateTourCodeFormat + "-" + Parser.intToStr(++lastCodeNumber, 3));
				preStm.setDate(2, Parser.stringToSqlDate(startDateList[i]));
				preStm.setInt(3, priceList[i]);
				preStm.setInt(4, priceForChildrenList[i]);
				preStm.setInt(5, priceForBabyList[i]);
				preStm.setInt(6, discountList[i]);
				preStm.setInt(7, totalSeatsList[i]);
				preStm.setInt(8, 0);
				preStm.setInt(9, ID);
				success = success && preStm.executeUpdate() > 0;
				
			}
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// delete
	// ========================================================================================== //
	
	public boolean delete(String code) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM Tour WHERE Code = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, code);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// edit: article, location list, views
	// ========================================================================================== //
	
	public boolean update(TourDTO dto) throws Exception{
		boolean success = false;
		try {
			String sql = "UPDATE Tour SET Price = ?, PriceForChildren = ?, PriceForbaby = ?, Discount = ?, TotalSeats = ? WHERE Code = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, dto.getPrice());
			preStm.setInt(2, dto.getPriceForChildren());
			preStm.setInt(3, dto.getPriceForBaby());
			preStm.setInt(4, dto.getDiscount());
			preStm.setInt(5, dto.getTotalSeats());
			preStm.setString(6, dto.getCode());
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// get attribute
	// ========================================================================================== //
	public TourDTO getTour(String code) throws Exception{
        TourDTO result = null;
        try {
            String sql = "SELECT StartDate, Price, PriceForChildren, PriceForBaby, Discount, TotalSeats, SeatsBooked, TourArticleID FROM Tour WHERE Code = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, code);
            rs = preStm.executeQuery();
            if (rs.next()){
				String startDate = Parser.sqlDateToString(rs.getDate("StartDate"));
				int price = rs.getInt("Price");
				int priceForChildren = rs.getInt("PriceForChildren");
				int priceForBaby = rs.getInt("PriceForBaby");
				int discount = rs.getInt("Discount");
				int totalSeats = rs.getInt("TotalSeats");
				int seatsBooked = rs.getInt("SeatsBooked");
				int tourArticleID = rs.getInt("TourArticleID");
                result = new TourDTO(code, startDate, price, priceForChildren, priceForBaby, discount, totalSeats, seatsBooked, tourArticleID);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
	
	public int getTourArticleID(String code) throws Exception{
        int tourArticleID = -1;
        try {
            String sql = "SELECT TourArticleID FROM Tour WHERE Code = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, code);
            rs = preStm.executeQuery();
            if (rs.next()){
				tourArticleID = rs.getInt("TourArticleID");
            }
        } finally{
            closeConnection();
        }
        return tourArticleID;
    }
	
	// get value
	// ========================================================================================== //
	
	public int getLastCodeNumber(String currentDateStr) throws Exception{
		int result = 0;
		try {
			String sql = "SELECT MAX(Code) AS MaxCode FROM Tour WHERE Code LIKE ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
			preStm.setString(1, "HNT-" + currentDateStr + "-" + "%");
            rs = preStm.executeQuery();
            rs.next();
            if (rs.getString("MaxCode") != null) result = Integer.parseInt(rs.getString("MaxCode").substring(11));
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public int getTourArticleMinPrice(int tourArticleID) throws Exception{
		int result = 0;
		try {
			String sql = "SELECT MIN(Price) AS Min FROM Tour WHERE TourArticleID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, tourArticleID);
			rs = preStm.executeQuery();
			rs.next();
			String resultStr = rs.getString("Min");
			if (resultStr == null) result = -1;
			else result = Integer.parseInt(resultStr);
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public int getTourArticleMaxDiscount(int tourArticleID) throws Exception{
		int result = 0;
		try {
			String sql = "SELECT MAX(Discount) AS Max FROM Tour WHERE TourArticleID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, tourArticleID);
			rs = preStm.executeQuery();
			rs.next();
			String resultStr = rs.getString("Max");
			if (resultStr != null) result = Integer.parseInt(resultStr);
		} finally{
			closeConnection();
		}
		return result;
	}
	
	// get list
	// ========================================================================================== //
	
	public List<String> getTourArticleTourDateList(int tourArticleID) throws Exception{
		List<String> tourDateList = new ArrayList();
		try {
			String sql = "SELECT TOP(3) StartDate FROM Tour WHERE TourArticleID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, tourArticleID);
			rs = preStm.executeQuery();
			while (rs.next()){
				String startDate = Parser.sqlDateToString(rs.getDate("StartDate"));
				tourDateList.add(startDate);
			}
		} finally{
			closeConnection();
		}
		return tourDateList;
	}
	
	public ListPack getTourList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<List<Object>> list = new ArrayList();
		try {
			String sql = "SELECT Code, StartDate, Departure, Destination, Price, TotalSeats, SeatsBooked, TourArticleID " +
						 "FROM Tour, TourArticle " +
						 "WHERE TourArticle.ID = Tour.TourArticleID " +
						 "ORDER BY StartDate DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				String code = rs.getString("Code");
				String startDate = Parser.sqlDateToString(rs.getDate("StartDate"));
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				int price = rs.getInt("Price");
				int totalSeats = rs.getInt("TotalSeats");
				int seatsBooked = rs.getInt("SeatsBooked");
				int tourArticleID = rs.getInt("TourArticleID");
				List tmpList = new ArrayList();
				tmpList.add(new TourDTO(code, startDate, price, 0, 0, 0, totalSeats, seatsBooked, tourArticleID));
				tmpList.add(departure);
				tmpList.add(destination);
				list.add(tmpList);
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getTourList(String search, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<List<Object>> list = new ArrayList();
		try{
			String sql = "SELECT Code, StartDate, Departure, Destination, Price, TotalSeats, SeatsBooked, TourArticleID " +
						 "FROM Tour, TourArticle " +
						 "WHERE (TourArticle.ID = Tour.TourArticleID) " +
						 "	AND ((TourArticle.Departure LIKE ?) OR (TourArticle.Destination LIKE ?)) " +
						 "ORDER BY StartDate DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preStm.setString(1, "%" + search + "%");
			preStm.setString(2, "%" + search + "%");
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				String code = rs.getString("Code");
				String startDate = Parser.sqlDateToString(rs.getDate("StartDate"));
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				int price = rs.getInt("Price");
				int totalSeats = rs.getInt("TotalSeats");
				int seatsBooked = rs.getInt("SeatsBooked");
				int tourArticleID = rs.getInt("TourArticleID");
				List tmpList = new ArrayList();
				tmpList.add(new TourDTO(code, startDate, price, 0, 0, 0, totalSeats, seatsBooked, tourArticleID));
				tmpList.add(departure);
				tmpList.add(destination);
				list.add(tmpList);
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public List<TourDTO> getTourArticleTourList(int tourArticleID) throws Exception{
		List<TourDTO> tourList = new ArrayList();
		try {
			String sql = "SELECT Code, StartDate, Price, PriceForChildren, PriceForBaby, Discount " +
						 "FROM Tour WHERE TourArticleID = ? " +
						 "ORDER BY StartDate";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, tourArticleID);
			rs = preStm.executeQuery();
			while (rs.next()){
				String code = rs.getString("Code");
				String startDate = Parser.sqlDateToString(rs.getDate("StartDate"));
				int price = rs.getInt("Price");
				int priceForChildren = rs.getInt("PriceForChildren");
				int priceForBaby = rs.getInt("PriceForBaby");
				int discount = rs.getInt("Discount");
				tourList.add(new TourDTO(code, startDate, price, priceForChildren, priceForBaby, discount, 0, 0, 0));
			}
		} finally{
			closeConnection();
		}
		return tourList;
	}
	
	public List<TourDTO> getTourListForAccount(List<BookingDTO> bookingList) throws Exception{
		List<TourDTO> list = new ArrayList();
		try {
			String sql = "SELECT Code, StartDate, Price, PriceForChildren, PriceForBaby, Discount, TourArticleID " +
						 "FROM Tour WHERE Code = ? ";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			for (BookingDTO dtoIter : bookingList){
				preStm.setString(1, dtoIter.getTourCode());
				rs = preStm.executeQuery();
				rs.next();
				String code = rs.getString("Code");
				String startDate = Parser.sqlDateToString(rs.getDate("StartDate"));
				int price = rs.getInt("Price");
				int priceForChildren = rs.getInt("PriceForChildren");
				int priceForBaby = rs.getInt("PriceForBaby");
				int discount = rs.getInt("Discount");
				int tourArticleID = rs.getInt("TourArticleID");
				list.add(new TourDTO(code, startDate, price, priceForChildren, priceForBaby, discount, -1, -1, tourArticleID));
			}
		} finally{
			closeConnection();
		}
		return list;
	}
	
	public int getSeatsAvailable(String code) throws Exception{
		int result = -1;
		try {
			String sql = "SELECT TotalSeats, SeatsBooked FROM Tour WHERE Code = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, code);
			rs = preStm.executeQuery();
			if (rs.next()){
				int totalSeats = rs.getInt("TotalSeats");
				int seatsBooked = rs.getInt("SeatsBooked");
				result = totalSeats - seatsBooked;
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public boolean increaseSeatsBooked(String code, int moreSeatsBooked) throws Exception{
		boolean success = false;
		try {
			String sql = "SELECT SeatsBooked FROM Tour WHERE Code = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, code);
			rs = preStm.executeQuery();
			if (rs.next()){
				int currentSeatsBooked = rs.getInt("SeatsBooked");
				sql = "UPDATE Tour SET SeatsBooked = ? WHERE Code = ?";
				preStm = conn.prepareStatement(sql);
				preStm.setInt(1, currentSeatsBooked + moreSeatsBooked);
				preStm.setString(2, code);
				success = preStm.executeUpdate() > 0;
			}
		} finally{
			closeConnection();
		}
		return success;
	}
	
}
