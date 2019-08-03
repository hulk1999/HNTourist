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
public class TourArticleDTO implements Serializable{
    
    private int ID;
    private String title;
	private String coverPhoto;
    private String duration;
    private String transport;
    private String departure;
    private String destination;
    private int views;
    private String article;
    private String uploaded;
    private String lastUpdate;

    public TourArticleDTO(int ID, String title, String coverPhoto, String duration, String transport, String departure, String destination, int views, String article, String uploaded, String lastUpdate) {
        this.ID = ID;
        this.title = title;
		this.coverPhoto = coverPhoto;
        this.duration = duration;
        this.transport = transport;
        this.departure = departure;
        this.destination = destination;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
