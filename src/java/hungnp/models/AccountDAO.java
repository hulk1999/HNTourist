/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.models;

import hungnp.database.HNConnection;
import hungnp.dtos.AccountDTO;
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
public class AccountDAO implements Serializable{

	// add
	// delete
	// edit
	// get attribute
	// check
	// get value
	// get list
	// ========================================================================================== //
	
    Connection conn;
    PreparedStatement preStm;
    ResultSet rs;
    
    public AccountDAO() {
    }
    
    private void closeConnection() throws Exception{
        if (rs != null) rs.close();
        if (preStm != null) preStm.close();
        if (conn != null) conn.close();
    }
    
	// add: account
	// ========================================================================================== //
	
    public boolean add(AccountDTO dto) throws Exception{
        boolean success = false;
        try {
            String sql = "INSERT INTO Account(Username, Password, Email, Admin, Created, LastUpdate) VALUES(?, ?, ?, ?, ?, ?)";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getUsername());
            preStm.setString(2, dto.getPassword());
            preStm.setString(3, Parser.emptyToNull(dto.getEmail()));
            preStm.setString(4, "0");
            preStm.setString(5, dto.getCreated());
            preStm.setString(6, dto.getLastUpdate());
            success = preStm.executeUpdate() > 0;
        } finally{
            closeConnection();
        }
        return success;
    }
    
	// delete: account
	// ========================================================================================== //
	
	public boolean delete(String username) throws Exception{
		boolean success = false;
		try {
			String sql = "DELETE FROM Account WHERE Username = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, username);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// edit: info, password, role
	// ========================================================================================== //
	
	public boolean update(AccountDTO dto) throws Exception{
        boolean success = false;
        try {
            String sql = "UPDATE Account SET FullName = ?, Email = ?, Phone = ?, Gender = ?, Birthdate = ?, Address = ?, Title = ?, Agency = ?, LastUpdate = ? WHERE Username = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, Parser.emptyToNull(dto.getFullName()));
            preStm.setString(2, Parser.emptyToNull(dto.getEmail()));
            preStm.setString(3, Parser.emptyToNull(dto.getPhone()));
            preStm.setString(4, Parser.emptyToNull(dto.getGender()));
            preStm.setDate(5, Parser.stringToSqlDate(Parser.emptyToNull(dto.getBirthdate())));
            preStm.setString(6, Parser.emptyToNull(dto.getAddress()));
            preStm.setString(7, Parser.emptyToNull(dto.getTitle()));
            preStm.setString(8, Parser.emptyToNull(dto.getAgency()));
            preStm.setString(9, dto.getLastUpdate());
			preStm.setString(10, dto.getUsername());
            success = preStm.executeUpdate() > 0;
        } finally{
            closeConnection();
        }
        return success;
    }
	
	public boolean setPassword(String username, String newPassword, String lastUpdate) throws Exception{
		boolean success = false;
		try {
			String sql = "UPDATE Account SET Password = ?, LastUpdate = ? WHERE Username = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, newPassword);
			preStm.setString(2, lastUpdate);
			preStm.setString(3, username);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	public boolean changeRole(String username, boolean admin) throws Exception{
		boolean success = false;
		try {
			String sql = "UPDATE Account SET Admin = ? WHERE Username = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, admin ? "0" : "1");
			preStm.setString(2, username);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
    
	// get attribute: admin, role, account
	// ========================================================================================== //
	
	public boolean isAdmin(String username) throws Exception{
		boolean result = false;
		try {
			String sql = "SELECT Admin FROM Account WHERE Username = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, username);
			rs = preStm.executeQuery();
			if (rs.next()){
				result = rs.getInt("Admin") == 1;
			}
		} finally{
			closeConnection();
		}
		return result;
	}
	
    public String getRole(String username) throws Exception{
        String role = "user";
        try {
            String sql = "SELECT Admin FROM Account WHERE Username = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            rs = preStm.executeQuery();
            if (rs.next()){
                if (rs.getInt("Admin") == 1) role = "admin";
            }
        } finally{
            closeConnection();
        }
        return role;
    }
    
    public AccountDTO getAccount(String username) throws Exception{
        AccountDTO result = null;
        try {
            String sql = "SELECT Username, Password, FullName, Email, Phone, Gender, Birthdate, Address, Title, Agency, Admin, Created, LastUpdate FROM Account WHERE Username = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            rs = preStm.executeQuery();
            if (rs.next()){
                String password = rs.getString("Password");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String gender = rs.getString("Gender");
                String birthdate = Parser.sqlDateToString(rs.getDate("Birthdate"));
                String address = rs.getString("Address");
                String title = rs.getString("Title");
                String agency = rs.getString("Agency");
                boolean admin = rs.getInt("Admin") == 1;
                String created = rs.getString("Created");
                String lastUpdate = rs.getString("LastUpdate");
                result = new AccountDTO(username, password, fullName, email, phone, gender, birthdate, address, title, agency, admin, created, lastUpdate);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
	
	// check: existence, valid
	// ========================================================================================== //
	
    public boolean checkExistence(String username) throws Exception{
        boolean result = false;
        try {
            String sql = "SELECT Username FROM Account WHERE Username = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            rs = preStm.executeQuery();
            result = rs.next();
        } finally{
            closeConnection();
        }
        return result;
    }
    
    public boolean checkLogin(String username, String password) throws Exception{
        boolean result = false;
        try {
            String sql = "SELECT Username FROM Account WHERE Username = ? AND Password = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, username);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            result = rs.next();
        } finally{
            closeConnection();
        }
        return result;
    }
	
	// get list: account admin, account search admin
	// ========================================================================================== //
	
	public ListPack getAccountList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<AccountDTO> list = new ArrayList();
		try {
			String sql = "SELECT Username, FullName, Gender, Admin, Created FROM Account " +
						 "ORDER BY Username, FullName";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = preStm.executeQuery();
			rs.last();
			itemCount = rs.getRow();
			int rowResultCount = Parser.getRowResultCount(page, itemsPerPage, itemCount);
			rs.absolute((page - 1)*itemsPerPage);
			for (int i = 0; i <= rowResultCount - 1; i++){
				rs.next();
				String username = rs.getString("Username");
				String fullName = rs.getString("FullName");
				String gender = rs.getString("Gender");
				boolean admin = rs.getInt("Admin") == 1;
				String created = Parser.sqlDateToString(rs.getDate("Created"));
				list.add(new AccountDTO(username, null, fullName, null, null, gender, null, null, null, null, admin, created, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getAccountList(String search, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<AccountDTO> list = new ArrayList();
		try {
			String adminCondition = "";
			if ("admin".contains(search.toLowerCase())) adminCondition += " OR (Admin = 1)";
			if ("khách hàng".contains(search.toLowerCase())) adminCondition += " OR (Admin = 0)";
			String sql = "SELECT Username, FullName, Gender, Admin, Created FROM Account " +
						 "WHERE (Username LIKE ?) OR (Fullname LIKE ?) OR (Gender LIKE ?)" + adminCondition +
						 "ORDER BY Username, FullName";
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
				String username = rs.getString("Username");
				String fullName = rs.getString("FullName");
				String gender = rs.getString("Gender");
				boolean admin = rs.getInt("Admin") == 1;
				String created = Parser.sqlDateToString(rs.getDate("Created"));
				list.add(new AccountDTO(username, null, fullName, null, null, gender, null, null, null, null, admin, created, null));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
    
}
