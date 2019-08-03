/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.models;

import hungnp.database.HNConnection;
import hungnp.dtos.LocationDTO;
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
public class LocationDAO {
	
	// add: location
	// delete: location
	// check: existence
	// get value: location search count
	// get list: region, location, location search admin
	// ========================================================================================== //
	
	Connection conn;
	PreparedStatement preStm;
	ResultSet rs;

	public LocationDAO() {
	}
	
	private void closeConnection() throws Exception{
		if (rs != null) rs.close();
		if (preStm != null) preStm.close();
		if (conn != null) conn.close();
	}
	
	// add: location
	// ========================================================================================== //
	
	public boolean add(LocationDTO dto) throws Exception{
		boolean success = false;
		try {
			String sql = "INSERT INTO Location(Location, ForeignLocation, Region) VALUES(?, ?, ?)";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, dto.getLocation());
			preStm.setString(2, dto.isForeign() ? "1" : "0");
			preStm.setString(3, dto.getRegion());
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// delete: location
	// ========================================================================================== //
	
	public boolean delete(String location) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM Location WHERE Location = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, location);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// check: existence
	// ========================================================================================== //
	
	public boolean checkExistence(String location) throws Exception{
		boolean result = false;
		try {
			String sql = "SELECT Location FROM Location WHERE Location = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, location);
			rs = preStm.executeQuery();
			result = rs.next();
		} finally{
			closeConnection();
		}
		return result;
	}
	
	// get list: region, location, location search admin
	// ========================================================================================== //
	
	public List<LocationDTO> getRegionList() throws Exception{
		List<LocationDTO> result = new ArrayList();
		try {
			String sql = "SELECT DISTINCT ForeignLocation, Region FROM Location";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			rs = preStm.executeQuery();
			while (rs.next()){
				boolean foreignLocation = rs.getInt("ForeignLocation") == 1;
				String region = rs.getString("Region");
				result.add(new LocationDTO(null, foreignLocation, region));
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public List<LocationDTO> getLocationList() throws Exception{
		List<LocationDTO> result = new ArrayList();
		try {
			String sql = "SELECT Location, ForeignLocation, Region FROM Location";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			rs = preStm.executeQuery();
			while (rs.next()){
				String location = rs.getString("Location");
				boolean foreignLocation = rs.getInt("ForeignLocation") == 1;
				String region = rs.getString("Region");
				result.add(new LocationDTO(location, foreignLocation, region));
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public ListPack getLocationList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<LocationDTO> list = new ArrayList();
		try {
			String sql = "SELECT Location, ForeignLocation, Region FROM Location " +
						 "ORDER BY ForeignLocation, Region, Location";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				String location = rs.getString("Location");
				boolean foreignLocation = rs.getInt("ForeignLocation") == 1;
				String region = rs.getString("Region");
				list.add(new LocationDTO(location, foreignLocation, region));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getLocationList(String search, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<LocationDTO> list = new ArrayList();
		try {
			String foreignCondition = "";
			if ("trong nước".contains(search.toLowerCase())) foreignCondition += " OR (ForeignLocation = 0)";
			if ("nước ngoài".contains(search.toLowerCase())) foreignCondition += " OR (ForeignLocation = 1)";
			String sql = "SELECT Location, ForeignLocation, Region FROM Location " +
						 "WHERE (Location LIKE ?) OR (Region LIKE ?)" + foreignCondition +
						 "ORDER BY ForeignLocation, Region, Location";
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
				String location = rs.getString("Location");
				boolean foreignLocation = rs.getInt("ForeignLocation") == 1;
				String region = rs.getString("Region");
				list.add(new LocationDTO(location, foreignLocation, region));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public List<List<Object>> getPopularLocationList(int locationNum) throws Exception{
		List<List<Object>> result = new ArrayList();
		try {
			String sql = "SELECT TOP(?) Location, COUNT(*) AS Count " +
						 "FROM LocationsOfArticles " +
						 "GROUP BY Location " +
						 "ORDER BY Count DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, locationNum);
			rs = preStm.executeQuery();
			while (rs.next()){
				String location = rs.getString("Location");
				int count = rs.getInt("Count");
				ArrayList tmpList = new ArrayList();
				tmpList.add(location);
				tmpList.add(count);
				result.add(tmpList);
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public List<String> getArticleLocations(int ID) throws Exception{
		List<String> locationList = new ArrayList();
		try {
			String sql = "SELECT Location FROM LocationsOfArticles WHERE ArticleID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			rs = preStm.executeQuery();
			while (rs.next()){
				String location = rs.getString("Location");
				locationList.add(location);
			}
		} finally{
			closeConnection();
		}
		return locationList;
	}
	
	public List<String> getTourArticleLocations(int ID) throws Exception{
		List<String> locationList = new ArrayList();
		try {
			String sql = "SELECT Location FROM LocationsOfTourArticles WHERE TourArticleID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			rs = preStm.executeQuery();
			while (rs.next()){
				String location = rs.getString("Location");
				locationList.add(location);
			}
		} finally{
			closeConnection();
		}
		return locationList;
	}
	
	public int getArticleAppearanceCount(String location) throws Exception{
		int result = 0;
		try {
			String sql = "SELECT COUNT(*) AS Count FROM LocationsOfArticles WHERE Location = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, location);
			rs = preStm.executeQuery();
			rs.next();
			result = rs.getInt("Count");
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public int getTourArticleAppearanceCount(String location) throws Exception{
		int result = 0;
		try {
			String sql = "SELECT COUNT(*) AS Count FROM LocationsOfTourArticles WHERE Location = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, location);
			rs = preStm.executeQuery();
			rs.next();
			result = rs.getInt("Count");
		} finally{
			closeConnection();
		}
		return result;
	}
	
}