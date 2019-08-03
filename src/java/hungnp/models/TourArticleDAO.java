/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.models;

import hungnp.database.HNConnection;
import hungnp.dtos.TourArticleDTO;
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
public class TourArticleDAO {

	// add
	// delete
	// edit
	// get attribute
	// get value
	// get list
	// ========================================================================================== //
	
    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;
    
    public TourArticleDAO() {
    }
    
    private void closeConnection() throws Exception{
        if (rs != null) rs.close();
        if (preStm != null) preStm.close();
        if (conn != null) conn.close();
    }
    
	// add: tour article
	// ========================================================================================== //
	
	public boolean add(TourArticleDTO dto) throws Exception{
        boolean success = false;
        try {
            String sql = "INSERT INTO TourArticle(ID, Title, CoverPhoto, Duration, Transport, Departure, Destination, Views, Article, Uploaded, LastUpdate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, Integer.toString(dto.getID()));
            preStm.setString(2, dto.getTitle());
			preStm.setString(3, dto.getCoverPhoto());
            preStm.setString(4, dto.getDuration());
            preStm.setString(5, dto.getTransport());
            preStm.setString(6, dto.getDeparture());
            preStm.setString(7, dto.getDestination());
            preStm.setString(8, Integer.toString(dto.getViews()));
            preStm.setString(9, dto.getArticle());
            preStm.setString(10, dto.getUploaded());
            preStm.setString(11, dto.getLastUpdate());
            success = preStm.executeUpdate() > 0;
        } finally{
            closeConnection();
        }
        return success;
    }
	
	public boolean addLocations(int ID, String[] locationList) throws Exception{
		if (locationList == null) return true;
		boolean success = false;
		try {
			String sql = "INSERT INTO LocationsOfTourArticles(TourArticleID, Location) VALUES(?, ?)";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			success = true;
			for (String location : locationList){
				preStm.setString(2, location);
				success = success && preStm.executeUpdate() > 0;
			}
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// delete
	// ========================================================================================== //
	
	public boolean delete(int ID) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM TourArticle WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// edit
	// ========================================================================================== //
	
	public boolean update(TourArticleDTO dto) throws Exception{
		boolean success = false;
		try {
			String sql = "UPDATE TourArticle SET Title = ?, CoverPhoto = ?, Duration = ?, Transport = ?, Departure = ?, Destination = ?, Article = ?, LastUpdate = ? WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, dto.getTitle());
			preStm.setString(2, dto.getCoverPhoto());
			preStm.setString(3, dto.getDuration());
			preStm.setString(4, dto.getTransport());
			preStm.setString(5, dto.getDeparture());
			preStm.setString(6, dto.getDestination());
			preStm.setString(7, dto.getArticle());
			preStm.setString(8, dto.getLastUpdate());
			preStm.setInt(9, dto.getID());
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	public boolean updateLocations(int ID, String[] locationList) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM LocationsOfTourArticles WHERE TourArticleID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			preStm.executeUpdate();
			success = true;
			if (locationList != null){
				sql = "INSERT INTO LocationsOfTourArticles(TourArticleID, Location) VALUES(?, ?)";
				preStm = conn.prepareStatement(sql);
				preStm.setInt(1, ID);
				for (String location : locationList){
					preStm.setString(2, location);
					success = success && preStm.executeUpdate() > 0;
				}
			}
		} finally{
			closeConnection();
		}
		return success;
	}
	
	public boolean increaseView(int ID) throws Exception{
		boolean success = false;
		try {
			int views = 0;
			String sql = "SELECT Views FROM TourArticle WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			rs = preStm.executeQuery();
			if (rs.next()){
				views = rs.getInt("Views");
				sql = "UPDATE TourArticle SET Views = ? WHERE ID = ?";
				preStm = conn.prepareStatement(sql);
				preStm.setInt(1, views + 1);
				preStm.setInt(2, ID);
				success = preStm.executeUpdate() > 0;
			}
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// get attribute: tour article
	// ========================================================================================== //
	
	public TourArticleDTO getTourArticleBookingRightSidebar(int ID) throws Exception{
        TourArticleDTO result = null;
        try {
            String sql = "SELECT Title, CoverPhoto, Duration, Destination FROM TourArticle WHERE ID = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, Integer.toString(ID));
            rs = preStm.executeQuery();
            if (rs.next()){
                String title = rs.getString("Title");
                String coverPhoto = rs.getString("CoverPhoto");
				String duration = rs.getString("Duration");
				String destination = rs.getString("Destination");
				result = new TourArticleDTO(ID, title, coverPhoto, duration, null, null, destination, 0, null, null, null);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
	
	public TourArticleDTO getTourArticleInfo(int ID) throws Exception{
        TourArticleDTO result = null;
        try {
            String sql = "SELECT Title, Duration, Transport, Departure, Destination FROM TourArticle WHERE ID = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, Integer.toString(ID));
            rs = preStm.executeQuery();
            if (rs.next()){
                String title = rs.getString("Title");
                String duration = rs.getString("Duration");
                String transport = rs.getString("Transport");
                String departure = rs.getString("Departure");
                String destination = rs.getString("Destination");
				result = new TourArticleDTO(ID, title, null, duration, transport, departure, destination, 0, null, null, null);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
	
    public TourArticleDTO getTourArticle(int ID) throws Exception{
        TourArticleDTO result = null;
        try {
            String sql = "SELECT Title, CoverPhoto, Duration, Transport, Departure, Destination, Article, LastUpdate FROM TourArticle WHERE ID = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, Integer.toString(ID));
            rs = preStm.executeQuery();
            if (rs.next()){
                String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
                String duration = rs.getString("Duration");
                String transport = rs.getString("Transport");
                String departure = rs.getString("Departure");
                String destination = rs.getString("Destination");
                String article = rs.getString("Article");
				String lastUpdate = Parser.sqlDateToString(rs.getDate("LastUpdate"));
                result = new TourArticleDTO(ID, title, coverPhoto, duration, transport, departure, destination, 0, article, null, lastUpdate);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    
	// get value: last id, tour article search count
	// ========================================================================================== //
	
    public int getLastID() throws Exception{
        int result = 0;
        try {
            String sql = "SELECT MAX(ID) AS MaxID FROM TourArticle";
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
	
	// get list: tour article search admin
	// ========================================================================================== //
	
	public ListPack getTourArticleList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<TourArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, Duration, Transport, Departure, Destination, Views FROM TourArticle " +
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
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String duration = rs.getString("Duration");
				String transport = rs.getString("Transport");
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				int views = rs.getInt("Views");
				list.add(new TourArticleDTO(ID, title, null, duration, transport, departure, destination, views, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getTourArticleList(String search, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<TourArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, Duration, Transport, Departure, Destination, Views FROM TourArticle " +
						 "WHERE (Title LIKE ?) OR (Departure LIKE ?) OR (Destination LIKE ?)" +
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
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String duration = rs.getString("Duration");
				String transport = rs.getString("Transport");
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				int views = rs.getInt("Views");
				list.add(new TourArticleDTO(ID, title, null, duration, transport, departure, destination, views, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getNewTourArticleShowList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<TourArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, Duration, Transport, Departure, Destination FROM TourArticle " +
						 "ORDER BY LastUpdate DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String duration = rs.getString("Duration");
				String transport = rs.getString("Transport");
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				list.add(new TourArticleDTO(ID, title, coverPhoto, duration, transport, departure, destination, 0, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getPopularTourArticleShowList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<TourArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, Duration, Transport, Departure, Destination FROM TourArticle " +
						 "ORDER BY Views DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String duration = rs.getString("Duration");
				String transport = rs.getString("Transport");
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				list.add(new TourArticleDTO(ID, title, coverPhoto, duration, transport, departure, destination, 0, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getRegionalTourArticleShowList(String region, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<TourArticleDTO> list = new ArrayList();
		try {
			int foreign = region.equals("local") ? 0 : 1;
			String sql = "SELECT ID, Title, CoverPhoto, Duration, Transport, Departure, Destination FROM TourArticle " +
						 "WHERE ID IN(" +
							 "SELECT DISTINCT TourArticleID FROM LocationsOfTourArticles, Location " +
							 "WHERE (ForeignLocation = ?) AND (LocationsOfTourArticles.Location = Location.Location)" +
							 ")" +
						 "ORDER BY LastUpdate DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preStm.setInt(1, foreign);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String duration = rs.getString("Duration");
				String transport = rs.getString("Transport");
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				list.add(new TourArticleDTO(ID, title, coverPhoto, duration, transport, departure, destination, 0, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getLocatedTourArticleShowList(String location, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<TourArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, Duration, Transport, Departure, Destination FROM TourArticle " +
						 "WHERE ID IN (SELECT DISTINCT TourArticleID FROM LocationsOfTourArticles WHERE Location = ?) " +
						 "ORDER BY LastUpdate DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preStm.setString(1, location);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String duration = rs.getString("Duration");
				String transport = rs.getString("Transport");
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				list.add(new TourArticleDTO(ID, title, coverPhoto, duration, transport, departure, destination, 0, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getTourArticleSearchShowList(String search, String fromDate, String toDate, String foreign, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<TourArticleDTO> list = new ArrayList();
		try {
			String condition = "WHERE ((Title LIKE ?) OR (Departure LIKE ?) OR (Destination LIKE ?)) ";
			if ((fromDate.length() > 0) || (toDate.length() > 0) || foreign.equals("Trong nước") || (foreign.equals("Ngoài nước"))){
				condition += "AND (ID IN (SELECT DISTINCT LocationsOfTourArticles.TourArticleID " +
										 "FROM LocationsOfTourArticles, Tour, Location " +
										 "WHERE (LocationsOfTourArticles.TourArticleID = Tour.TourArticleID) AND (LocationsOfTourArticles.Location = Location.Location) ";
				if (fromDate.length() > 0) condition += "AND (StartDate >= ?) ";
				if (toDate.length() > 0) condition += "AND (StartDate <= ?) ";
				if (foreign.equals("Trong nước")) condition += "AND (ForeignLocation = 0)";
				else if (foreign.equals("Ngoài nước")) condition += "AND (ForeignLocation = 1)";
				condition += "))";
			}
			String sql = "SELECT ID, Title, CoverPhoto, Duration, Transport, Departure, Destination FROM TourArticle " +
						 condition +
						 "ORDER BY LastUpdate DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int questionMarkCount = 0;
			preStm.setString(++questionMarkCount, "%" + search + "%");
			preStm.setString(++questionMarkCount, "%" + search + "%");
			preStm.setString(++questionMarkCount, "%" + search + "%");
			if (fromDate.length() > 0) preStm.setDate(++questionMarkCount, Parser.stringToSqlDate(fromDate));
			if (toDate.length() > 0) preStm.setDate(++questionMarkCount, Parser.stringToSqlDate(toDate));
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String duration = rs.getString("Duration");
				String transport = rs.getString("Transport");
				String departure = rs.getString("Departure");
				String destination = rs.getString("Destination");
				list.add(new TourArticleDTO(ID, title, coverPhoto, duration, transport, departure, destination, 0, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
}