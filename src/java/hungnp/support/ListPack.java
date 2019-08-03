/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.support;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ListPack implements Serializable{

	private int pageCount;
	private int pageShow;
	private List itemList;
	
	public ListPack() {
	}

	public ListPack(int pageCount, int pageShow, List itemList) {
		this.pageCount = pageCount;
		this.pageShow = pageShow;
		this.itemList = itemList;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageShow() {
		return pageShow;
	}

	public void setPageShow(int pageShow) {
		this.pageShow = pageShow;
	}

	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}
	
}
