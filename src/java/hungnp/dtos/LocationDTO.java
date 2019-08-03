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
public class LocationDTO {
	
	private String location;
	private boolean foreign;
	private String region;

	public LocationDTO() {
	}

	public LocationDTO(String location, boolean foreign, String region) {
		this.location = location;
		this.foreign = foreign;
		this.region = region;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isForeign() {
		return foreign;
	}

	public void setForeign(boolean foreign) {
		this.foreign = foreign;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
}
