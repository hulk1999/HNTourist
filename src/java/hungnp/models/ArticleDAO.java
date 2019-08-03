/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.models;

import hungnp.database.HNConnection;
import hungnp.dtos.ArticleDTO;
import hungnp.support.ListPack;
import hungnp.support.Parser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;

/**
 *
 * @author DELL
 */
public class ArticleDAO {
	
	// add: article, location list
	// delete: article
	// edit: article, location list, views
	// get attribute: article, location list
	// get value: last id, article search count
	// get list: article search admin, article new right sidebar, article popular right sidebar
	//			 article new show
	// ========================================================================================== //
	
	Connection conn;
	PreparedStatement preStm;
	ResultSet rs;

	public ArticleDAO() {
	}
	
	private void closeConnection() throws Exception{
		if (rs != null) rs.close();
		if (preStm != null) preStm.close();
		if (conn != null) conn.close();
	}
	
	// add: article, location list
	// ========================================================================================== //
	
	public boolean add(ArticleDTO dto) throws Exception{
		boolean success = false;
		try {
			String sql = "INSERT INTO Article(ID, Title, CoverPhoto, Views, Article, Uploaded, LastUpdate) VALUES(?, ?, ?, ?, ?, ?, ?)";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, dto.getID());
			preStm.setString(2, dto.getTitle());
			preStm.setString(3, dto.getCoverPhoto());
			preStm.setInt(4, dto.getViews());
			preStm.setString(5, dto.getArticle());
			preStm.setString(6, dto.getUploaded());
			preStm.setString(7, dto.getLastUpdate());
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
			String sql = "INSERT INTO LocationsOfArticles(ArticleID, Location) VALUES(?, ?)";
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
	
	// delete: article
	// ========================================================================================== //
	
	public boolean delete(int ID) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM Article WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// edit: article, location list, views
	// ========================================================================================== //
	
	public boolean update(ArticleDTO dto) throws Exception{
		boolean success = false;
		try {
			String sql = "UPDATE Article SET Title = ?, CoverPhoto = ?, Article = ?, LastUpdate = ? WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, dto.getTitle());
			preStm.setString(2, dto.getCoverPhoto());
			preStm.setString(3, dto.getArticle());
			preStm.setString(4, dto.getLastUpdate());
			preStm.setInt(5, dto.getID());
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	public boolean updateLocations(int ID, String[] locationList) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM LocationsOfArticles WHERE ArticleID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			preStm.executeUpdate();
			success = true;
			if (locationList != null){
				sql = "INSERT INTO LocationsOfArticles(ArticleID, Location) VALUES(?, ?)";
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
			String sql = "SELECT Views FROM Article WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			rs = preStm.executeQuery();
			if (rs.next()){
				views = rs.getInt("Views");
				sql = "UPDATE Article SET Views = ? WHERE ID = ?";
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
	
	// get attribute: article, location list
	// ========================================================================================== //
	
	public ArticleDTO getArticle(int ID) throws Exception{
		ArticleDTO dto = null;
		try {
			String sql = "SELECT Title, CoverPhoto, Article, LastUpdate FROM Article WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, ID);
			rs = preStm.executeQuery();
			if (rs.next()){
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String article = rs.getString("Article");
				String lastUpdate = Parser.sqlDateToString(rs.getDate("LastUpdate"));
				dto = new ArticleDTO(ID, title, coverPhoto, 0, article, null, lastUpdate);
			}
		} finally{
			closeConnection();
		}
		return dto;
	}
	
	public ArticleDTO getNewArticleForHomePage() throws Exception{
		ArticleDTO dto = null;
		try {
			String sql = "SELECT TOP(1) ID, Title, CoverPhoto, Article FROM Article ORDER BY LastUpdate DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			rs = preStm.executeQuery();
			if (rs.next()){
				int id = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String article = Jsoup.parse(rs.getString("Article")).text().substring(0, 200);
				dto = new ArticleDTO(id, title, coverPhoto, 0, article, null, null);
			}
		} finally{
			closeConnection();
		}
		return dto;
	}
	
	// get value: last id, article search count
	// ========================================================================================== //
	
	public int getLastID() throws Exception{
        int result = 0;
        try {
            String sql = "SELECT MAX(ID) AS MaxID FROM Article";
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
	
	// get list: article admin, article search admin, article new right sidebar,
	//			 article popular right sidebar, article new show
	// ========================================================================================== //
	
	public ListPack getArticleList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<ArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, Views FROM Article " +
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
				int views = rs.getInt("Views");
				list.add(new ArticleDTO(ID, title, null, views, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getArticleList(String search, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<ArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, Views FROM Article " +
						 "WHERE Title LIKE ? " +
						 "ORDER BY ID DESC";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preStm.setString(1, "%" + search + "%");
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				int views = rs.getInt("Views");
				list.add(new ArticleDTO(ID, title, null, views, null, null, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public List<ArticleDTO> getNewArticleList(int itemNum) throws Exception{
		List<ArticleDTO> result = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, LastUpdate " +
						 "FROM(" +
							 "SELECT ID, Title, CoverPhoto, LastUpdate, " +
							 "ROW_NUMBER() OVER (ORDER BY LastUpdate DESC) AS RowNum " +
							 "FROM Article" +
						 ") AS ItemListAll " +
						 "WHERE ItemListAll.RowNum BETWEEN 1 AND ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, itemNum);
			rs = preStm.executeQuery();
			while (rs.next()){
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String lastUpdate = Parser.sqlDateToString(rs.getDate("LastUpdate"));
				result.add(new ArticleDTO(ID, title, coverPhoto, 0, null, null, lastUpdate));
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public List<ArticleDTO> getPopularArticleList(int itemNum) throws Exception{
		List<ArticleDTO> result = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, LastUpdate " +
						 "FROM(" +
							 "SELECT ID, Title, CoverPhoto, LastUpdate, " +
							 "ROW_NUMBER() OVER (ORDER BY Views DESC) AS RowNum " +
							 "FROM Article" +
						 ") AS ItemListAll " +
						 "WHERE ItemListAll.RowNum BETWEEN 1 AND ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, itemNum);
			rs = preStm.executeQuery();
			while (rs.next()){
				int ID = rs.getInt("ID");
				String title = rs.getString("Title");
				String coverPhoto = rs.getString("CoverPhoto");
				String lastUpdate = Parser.sqlDateToString(rs.getDate("LastUpdate"));
				result.add(new ArticleDTO(ID, title, coverPhoto, 0, null, null, lastUpdate));
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
	public ListPack getNewArticleShowList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<ArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, Article, LastUpdate FROM Article " +
						 "ORDER BY LastUpdate DESC";
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
				String coverPhoto = rs.getString("CoverPhoto");
				String article = Jsoup.parse(rs.getString("Article")).text().substring(0, 400);
				String lastUpdate = Parser.sqlDateToString(rs.getDate("LastUpdate"));
				list.add(new ArticleDTO(ID, title, coverPhoto, 0, article, null, lastUpdate));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getPopularArticleShowList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<ArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, Article, LastUpdate FROM Article " +
						 "ORDER BY Views DESC";
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
				String coverPhoto = rs.getString("CoverPhoto");
				String article = Jsoup.parse(rs.getString("Article")).text().substring(0, 400);
				String lastUpdate = Parser.sqlDateToString(rs.getDate("LastUpdate"));
				list.add(new ArticleDTO(ID, title, coverPhoto, 0, article, null, lastUpdate));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getLocatedArticleShowList(String location, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<ArticleDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Title, CoverPhoto, Article, LastUpdate FROM Article " +
						 "WHERE ID IN (SELECT DISTINCT ArticleID FROM LocationsOfArticles WHERE Location = ?) " +
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
				String article = Jsoup.parse(rs.getString("Article")).text().substring(0, 400);
				String lastUpdate = Parser.sqlDateToString(rs.getDate("LastUpdate"));
				list.add(new ArticleDTO(ID, title, coverPhoto, 0, article, null, lastUpdate));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
}
