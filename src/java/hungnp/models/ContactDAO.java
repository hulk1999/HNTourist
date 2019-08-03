/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.models;

import hungnp.database.HNConnection;
import hungnp.dtos.ContactDTO;
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
public class ContactDAO implements Serializable{
	
	// add
	// delete
	// edit
	// get attribute
	// get value
	// ========================================================================================== //
	
	Connection conn;
	PreparedStatement preStm;
	ResultSet rs;

	public ContactDAO() {
	}
	
	private void closeConnection() throws Exception{
		if (rs != null) rs.close();
		if (preStm != null) preStm.close();
		if (conn != null) conn.close();
	}
	
	// add
	// ========================================================================================== //
	
	public boolean add(ContactDTO dto) throws Exception{
		boolean success = false;
		try {
			String sql = "INSERT INTO Contact(ID, Name, Email, Phone, Address, Message, SentTime, Processed) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setInt(1, dto.getID());
			preStm.setString(2, dto.getName());
			preStm.setString(3, dto.getEmail());
			preStm.setString(4, dto.getPhone());
			preStm.setString(5, dto.getAddress());
			preStm.setString(6, dto.getMessage());
			preStm.setString(7, dto.getSentTime());
			preStm.setInt(8, 0);
			success = preStm.executeUpdate() > 0;
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
			String sql = "DELETE FROM Contact WHERE ID = ?";
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
	
	public boolean changeProcessed(int ID, boolean processed) throws Exception{
		boolean success = false;
		try {
			String sql = "UPDATE Contact SET Processed = ? WHERE ID = ?";
			conn = HNConnection.getConnection();
			preStm = conn.prepareStatement(sql);
			preStm.setString(1, processed ? "0" : "1");
			preStm.setInt(2, ID);
			success = preStm.executeUpdate() > 0;
		} finally{
			closeConnection();
		}
		return success;
	}
	
	// get attribute
	// ========================================================================================== //
	
	public ContactDTO getContact(int ID) throws Exception{
        ContactDTO result = null;
        try {
            String sql = "SELECT Name, Email, Phone, Address, Message, SentTime, Processed FROM Contact WHERE ID = ?";
            conn = HNConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, ID);
            rs = preStm.executeQuery();
            if (rs.next()){
				String name = rs.getString("Name");
				String email = rs.getString("Email");
				String phone = rs.getString("Phone");
				String address = rs.getString("Address");
				String message = rs.getString("Message");
				String sentTime = Parser.timeToTimeStr(rs.getTimestamp("SentTime").getTime());
				boolean processed = rs.getInt("Processed") == 1;
                result = new ContactDTO(ID, name, email, phone, address, message, sentTime, processed);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
	
	// get value
	// ========================================================================================== //
	
	public int getLastID() throws Exception{
        int result = 0;
        try {
            String sql = "SELECT MAX(ID) AS MaxID FROM Contact";
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
	
	// get list
	// ========================================================================================== //
	
	public ListPack getContactList(int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<ContactDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Name, Message, SentTime, Processed FROM Contact " +
						 "ORDER BY Processed, SentTime DESC";
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
				String name = rs.getString("Name");
				String message = rs.getString("Message");
				if (message.length() > 100) message = message.substring(0, 100) + "...";
				String sentTime = Parser.timeToTimeStr(rs.getTimestamp("SentTime").getTime());
				boolean processed = rs.getInt("Processed") == 1;
				list.add(new ContactDTO(ID, name, null, null, null, message, sentTime, processed));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
	public ListPack getContactList(String search, int page, int itemsPerPage) throws Exception{
		int itemCount = 0;
		List<ContactDTO> list = new ArrayList();
		try {
			String sql = "SELECT ID, Name, Message, SentTime, Processed FROM Contact " +
						 "WHERE (Name LIKE ?) OR (Message LIKE ?)" +
						 "ORDER BY Processed, SentTime DESC";
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
				int ID = rs.getInt("ID");
				String name = rs.getString("Name");
				String message = rs.getString("Message");
				if (message.length() > 100) message = message.substring(0, 100) + "...";
				String sentTime = rs.getString("SentTime");
				boolean processed = rs.getInt("Processed") == 1;
				list.add(new ContactDTO(ID, name, null, null, null, message, sentTime, processed));
			}
		} finally{
			closeConnection();
		}
		return new ListPack(Parser.getPageCount(itemCount, itemsPerPage), page, list);
	}
	
}
