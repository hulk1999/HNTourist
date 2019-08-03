/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.dtos;

/**
 *
 * @author DELL
 */
public class ArticleDTO {

	private int ID;
	private String title;
	private String coverPhoto;
	private int views;
	private String article;
	private String uploaded;
	private String lastUpdate;
	
	public ArticleDTO() {
	}

	public ArticleDTO(int ID, String title, String coverPhoto, int views, String article, String uploaded, String lastUpdate) {
		this.ID = ID;
		this.title = title;
		this.coverPhoto = coverPhoto;
		this.views = views;
		this.article = article;
		this.uploaded = uploaded;
		this.lastUpdate = lastUpdate;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getUploaded() {
		return uploaded;
	}

	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
