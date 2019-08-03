/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.support;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author DELL
 */
public class Parser implements Serializable{
	
	// util.Date | sql.Date | String dd/MM/yyyy
	// String dd/MM/yyyy | VNmeseDateString dd Tháng MM, yyyy
	// String dd/MM/yyyy | day | month | year
	// String dd/MM/yyyy | tourCodeDateFormat
	// subtract days
	// time | timeString
	
	// int | VNmeseCurrency
	// int | StringSetLength
	// String[] | int[]
	
	// "" | null | &nbsp;
	// item info | url
	// queryString | urlForPageNavigation
	// bookingStatus | color
	
	// page, itemCount, iteamsPerPage | pageCount, pageShow, rowsCount
	
	// ========================================================================================== //
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	// util.Date | sql.Date | String dd/MM/yyyy
	// ========================================================================================== //
    public static java.util.Date sqlDateToUtilDate(java.sql.Date date){
        return date == null ? null : new java.util.Date(date.getTime());
    }
    
    public static String sqlDateToString(java.sql.Date date){
        return date == null ? null : SDF.format(sqlDateToUtilDate(date));
    }
    
    public static java.util.Date stringToUtilDate(String date) throws ParseException{
        return date == null ? null : SDF.parse(date);
    }
    
    public static java.sql.Date stringToSqlDate(String date) throws ParseException{
        return date == null ? null : new java.sql.Date(stringToUtilDate(date).getTime());
    }
	
	// String dd/MM/yyyy | VNmeseDateString dd Tháng MM, yyyy | Calendar
	// ========================================================================================== //
	public static Calendar stringToCalendar(String date) throws ParseException{
		java.util.Date utilDate = stringToUtilDate(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(utilDate);
		return calendar;
	}
	
	public static String calendarToString(Calendar calendar){
		String day = intToStr(calendar.get(Calendar.DAY_OF_MONTH), 2);
		String month = intToStr(calendar.get(Calendar.MONTH) + 1, 2);
		String year = Integer.toString(calendar.get(Calendar.YEAR));
		return day + "/" + month + "/" + year;
	}
	
	public static String stringToVNmeseDateString(String date) throws ParseException{
		Calendar calendar = stringToCalendar(date);
		String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String year = Integer.toString(calendar.get(Calendar.YEAR));
		return day + " Tháng " + month + ", " + year;
	}
	
	// String dd/MM/yyyy | day | month | year
	// ========================================================================================== //
	public static int stringToDay(String date) throws ParseException{
		return stringToCalendar(date).get(Calendar.DAY_OF_MONTH);
	}
	
	public static int stringToMonth(String date) throws ParseException{
		return stringToCalendar(date).get(Calendar.MONTH) + 1;
	}
	
	public static int stringToYear(String date) throws ParseException{
		return stringToCalendar(date).get(Calendar.YEAR);
	}
	
	// String dd/MM/yyyy | tourCodeDateFormat
	// ========================================================================================== //
	public static String stringToTourCodeDateFormat(String date) throws Exception{
		String day = intToStr(stringToDay(date), 2);
		String month = intToStr(stringToMonth(date) + 1, 2);
		String year = Integer.toString(stringToYear(date));
		year = year.substring(2);
		return day + month + year;
	}
	
	// subtract days
	// ========================================================================================== //
	public static String subtractDays(String currentDate, int days) throws ParseException{
		Calendar calendar = stringToCalendar(currentDate);
		calendar.add(Calendar.DATE, -days);
		return calendarToString(calendar);
	}
	
	// time | timeStr
	// ========================================================================================== //
	public static String timeToTimeStr(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		String result = "";
		result += intToStr(calendar.get(Calendar.DAY_OF_MONTH), 2) + "/";
		result += intToStr(calendar.get(Calendar.MONTH) + 1, 2) + "/";
		result += intToStr(calendar.get(Calendar.YEAR), 4) + " ";
		result += intToStr(calendar.get(Calendar.HOUR_OF_DAY), 2) + ":";
		result += intToStr(calendar.get(Calendar.MINUTE), 2) + ":";
		result += intToStr(calendar.get(Calendar.SECOND), 2);
		return result;
	}
	
	// ========================================================================================== //
	
	// int | VNmeseCurrency
	// ========================================================================================== //
	public static String intToVNmeseCurrency(int ammount){
		String result = "";
		while (ammount > 999){
			String tmp = Integer.toString(ammount % 1000);
			while (tmp.length() < 3) tmp = "0" + tmp;
			result = "." + tmp + result;
			ammount /= 1000;
		}
		result = Integer.toString(ammount) + result;
		return result;
	}
	
	// int | StringSetLength
	// ========================================================================================== //
	public static String intToStr(int num, int strLength){
		String result = Integer.toString(num);
		while (result.length() < strLength) result = "0" + result;
		return result;
	}
	
	// String[] | int[]
	// ========================================================================================== //
	public static int[] stringArrToIntArr(String[] input){
		if (input == null) return null;
		int length = input.length;
		int[] result = new int[length];
		for (int i = 0; i <= length - 1; i++)
			result[i] = input[i].length() == 0 ? 0 : Integer.parseInt(input[i]);
		return result;
	}
	
	// ========================================================================================== //
	
	// "" | null | &nbsp;
	// ========================================================================================== //
	public static String emptyToNull(String input){
        if (input == null) return null;
        return input.length() > 0 ? input : null;
    }
    
    public static String nullToEmpty(String input){
        return input == null ? "" : input;
    }
	
	public static String nullToSpace(String input){
		return input == null ? "&nbsp;" : input;
	}
	
	// item info | url
	// ========================================================================================== //
	public static String articleInfoToUrl(String contextPath, int ID, String title){
		return contextPath + "/article?id=" + Integer.toString(ID) + "&title=" + title;
	}
	
	public static String pageInfoToUrl(String currentUrl, String search, String show, int page){
		String searchUrlParam = search == null ? "" : "?search=" + search;
		String showUrlParam = "";
		if (show != null){
			showUrlParam = search == null ? "?" : "&";
			showUrlParam += "show=" + show;
		}
		String pageUrlParam = (searchUrlParam.length() == 0) && (showUrlParam.length() == 0) ? "?" : "&";
		pageUrlParam += "page=" + Integer.toString(page);
		return currentUrl + searchUrlParam + showUrlParam + pageUrlParam;
	}
	
	// queryString | urlForPageNavigation
	// ========================================================================================== //
	public static String queryStrToUrlForPageNavigation(String contextPath, String currentPage, String queryStr){
		if (queryStr == null) queryStr = "page=";
		else if (queryStr.length() == 0) queryStr = "page=";
		else if (queryStr.contains("page=")) queryStr = queryStr.substring(0, queryStr.indexOf("page=") + 5);
		else queryStr += "&page=";
		return contextPath + "/" + currentPage + "?" + queryStr;
	}
	
	// bookingStatus | color
	// ========================================================================================== //
	public static String statusToColor(String status){
		if (status.equals("Chưa thanh toán")) return "orange";
		if (status.equals("Đã thanh toán")) return "green";
		if (status.equals("Hết hạn")) return "red";
		return "gray";
	}
	
	// ========================================================================================== //
	
	// page, itemCount, iteamsPerPage | pageCount, pageShow, rowsCount
	// ========================================================================================== //
	public static int getPageCount(int itemCount, int itemsPerPage){
		return (int) Math.ceil(1.0 * itemCount / itemsPerPage);
	}
	
	public static int getPageShow(String pageShow) throws Exception{
		return pageShow == null ? 1 : Integer.parseInt(pageShow);
	}
	
	public static int getRowResultCount(int page, int itemsPerPage, int itemCount){
		int fromRow = (page - 1)*itemsPerPage + 1;
		int rowResultCount;
		if (fromRow > itemCount) rowResultCount = 0;
		else rowResultCount = Integer.min(itemsPerPage, itemCount - fromRow + 1);
		return rowResultCount;
	}
	
}
