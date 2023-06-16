package com.jdl.basic.api.enums;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FrequencyTypeEnum
 * @Description 执行周期 D-每天 W-每周 M-每月
 * @Author wyd
 * @Date 2023/5/30 16:49
 **/
public enum FrequencyTypeEnum {
	DAILY ("D", "每天"),
	WEEKLY ("W", "每周"),
	MONTHLY ("M", "每月"),
    ;
	
	private static Map<String,List<FrequencyItem>> itemsListMap = new HashMap<>();
	
	private FrequencyTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	static {
		List<FrequencyItem> dList = new ArrayList<>();
		dList.add(new FrequencyItem(1,"1","当天"));
		itemsListMap.put(DAILY.code, dList);
		
		List<FrequencyItem> wList = new ArrayList<>();
		wList.add(new FrequencyItem(2,"2","周一"));
		wList.add(new FrequencyItem(3,"3","周二"));
		wList.add(new FrequencyItem(4,"4","周三"));
		wList.add(new FrequencyItem(5,"5","周四"));
		wList.add(new FrequencyItem(6,"6","周五"));
		wList.add(new FrequencyItem(7,"7","周六"));
		wList.add(new FrequencyItem(1,"1","周日"));
		itemsListMap.put(WEEKLY.code, wList);
		
		List<FrequencyItem> mList = new ArrayList<>();
		for(int i=1; i<=31; i++) {
			mList.add(new FrequencyItem(i,i+"",i+"号"));
		}
		itemsListMap.put(MONTHLY.code, mList);		
	}
	private String code;
	private String name;
	/**
	 * 根据code获取名称
	 * @param code
	 * @return
	 */
    public static String getNameByCode(String code) {
    	FrequencyTypeEnum data = getEnum(code);
    	if(data != null) {
    		return data.getName();
    	}
        return "";
    }
	public List<FrequencyItem> getFrequencyItems() {
		if(itemsListMap.containsKey(code)) {
			return itemsListMap.get(code);
		}
		return new ArrayList<>();
	}
	public String getFrequencyItemName(String itemCode) {
		FrequencyItem itemData = getFrequencyItem(itemCode);
		if(itemData != null) {
			return itemData.getName();
		}
		return "";
	}
	public FrequencyItem getFrequencyItem(String itemCode) {
		if(itemCode != null) {
			try {
				return getFrequencyItem(Integer.valueOf(itemCode));
			}catch(Exception e) {
				
			}
		}
		return null;
	}	
	public FrequencyItem getFrequencyItem(Integer order) {
		if(order != null
				&& order > 0
				&& itemsListMap.containsKey(code)) {
			List<FrequencyItem> list = itemsListMap.get(code);
			if(list.size() >= order) {
				return list.get(order - 1);
			}
		}
		return null;
	}
	public Date getNextTime(Date startTime,int frequencyHour, int frequencyMinute, List<Integer> dayList){
		Date curTime = startTime;
		if(curTime == null) {
			curTime = new Date();
		}
        Collections.sort(dayList);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curTime);
        calendar.set(Calendar.HOUR_OF_DAY, frequencyHour);
        calendar.set(Calendar.MINUTE, frequencyMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        if(DAILY.code.equals(code)) {
    		if(calendar.getTime().after(curTime)) {
    			return calendar.getTime();
    		}else {
            	calendar.add(Calendar.DAY_OF_YEAR, 1);
            	return calendar.getTime();
    		}
        }
        
        int field = Calendar.DAY_OF_WEEK;
        if(MONTHLY.code.equals(code)) {
        	field = Calendar.DAY_OF_MONTH;
        }
        
        int curDay = calendar.get(field);
        int nextDay = 0;
        for(int day : dayList) {
        	if(day == curDay) {
        		if(calendar.getTime().after(curTime)) {
        			return calendar.getTime();
        		}
        	}else if(day > curDay) {
        		nextDay = day;
        		break;
        	}
        }
        if(nextDay == 0) {
        	calendar.set(field, dayList.get(0));
        	if(MONTHLY.code.equals(code)){
        		calendar.add(Calendar.MONTH, 1);
        	}else {
        		calendar.add(Calendar.DATE, 7);
        	}
        	return calendar.getTime();
        }else {
        	calendar.set(field, nextDay);
        	return calendar.getTime();
        }
	}
	/**
	 * 根据code获取enum
	 * @param code
	 * @return
	 */
    public static FrequencyTypeEnum getEnum(String code) {
        for (FrequencyTypeEnum value : FrequencyTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
    /**
     * 判断code是否存在
     * @param code
     * @return
     */
    public boolean exist(String code) {
        return null != getEnum(code);
    }

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static class FrequencyItem{
		
		private Integer order;
		private String code;
		private String name;
		
		public FrequencyItem(Integer order, String code, String name) {
			super();
			this.order = order;
			this.code = code;
			this.name = name;
		}
		public Integer getOrder() {
			return order;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
}
