package com.core.tools;

/**
 * 计时器
 * 
 * @author Yonghua Liu
 * @email yliu19930120@163.com
 * @date 2018年4月21日下午5:45:27
 */
public class Timer {
	
	 public static String getTimeFormat(Long startTime){
	    	long endTime   = System.currentTimeMillis(); 
	    	long TotalTime = endTime - startTime; 
			return formatTime(TotalTime);
	}
	 
	 public static Long getTime(Long startTime){
	    	long endTime   = System.currentTimeMillis(); 
	    	long TotalTime = endTime - startTime; 
			return TotalTime;
	}
	 public static String formatTime(Long ms) {  
		    Integer ss = 1000;  
		    Integer mi = ss * 60;  
		    Integer hh = mi * 60;  
		    Integer dd = hh * 24;  
		  
		    Long day = ms / dd;  
		    Long hour = (ms - day * dd) / hh;  
		    Long minute = (ms - day * dd - hour * hh) / mi;  
		    Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
		    Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  
		      
		    StringBuffer sb = new StringBuffer();  
		    if(day > 0) {  
		        sb.append(day+"天");  
		    }  
		    if(hour > 0) {  
		        sb.append(hour+"小时");  
		    }  
		    if(minute > 0) {  
		        sb.append(minute+"分");  
		    }  
		    if(second > 0) {  
		        sb.append(second+"秒");  
		    }  
		    if(milliSecond > 0) {  
		        sb.append(milliSecond+"毫秒");  
		    }  
		    return sb.toString();  
		}  
}
