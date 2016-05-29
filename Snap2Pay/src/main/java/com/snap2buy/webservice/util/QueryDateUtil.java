package com.snap2buy.webservice.util;


import com.snap2buy.webservice.model.InputObject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class QueryDateUtil {
	
	public String getISOWeekID(String startDate, String endDate,String frequency)  throws Exception{
		
		
		return null;
	}

    private static String dateFormatter(Calendar cal,DateTime week) throws ParseException{
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = (Date)formatter.parse(week.toDate().toString());

        cal.setTime(date);


        String month;
        if(Integer.valueOf((cal.get(Calendar.MONTH) + 1))<10){
            month="0"+String.valueOf((cal.get(Calendar.MONTH) + 1));

        }else{
            month=String.valueOf((cal.get(Calendar.MONTH) + 1));
        }

        String dateNum;
        if(Integer.valueOf(cal.get(Calendar.DATE))<10){
            dateNum="0"+String.valueOf(cal.get(Calendar.DATE));

        }else{
            dateNum=String.valueOf(cal.get(Calendar.DATE));
        }

        String yearNum;
        yearNum=String.valueOf( cal.get(Calendar.YEAR));
        String formatedDate =  yearNum+month+dateNum;

        System.out.println("formatedDate : " + formatedDate);
        return formatedDate;
    }
	
	public static void setBaseDates(InputObject input) throws Exception {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        DateFormat monthFormat = new SimpleDateFormat("yyyyMM");

        Calendar cal = Calendar.getInstance();
        if (input.getFrequency().equals("DAILY")) {

            input.setPrevStartTime(input.getStartDate());
            input.setPrevEndTime(input.getStartDate());

            input.setStartTime(input.getEndDate());
            input.setEndTime(input.getEndDate());
        } else if (input.getFrequency().equals("WEEKLY")) {

            //String startWeekId = createWeekId(input.getStartDate());
            //String endWeekId = createWeekId(input.getEndDate());

            cal.setTime(format.parse(input.getStartDate()));
            DateTime preWeek = new DateTime(cal);
            DateTime weekStart = preWeek.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay();
            DateTime weekEnd = preWeek.withDayOfWeek(DateTimeConstants.SUNDAY).withTimeAtStartOfDay();

            input.setPrevStartTime(dateFormatter(cal, weekStart));
            input.setPrevEndTime(dateFormatter(cal, weekEnd));


            cal.setTime(format.parse(input.getEndDate()));
            DateTime currWeek = new DateTime(cal);
            DateTime currweekStart = currWeek.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay();
            DateTime currweekEnd = currWeek.withDayOfWeek(DateTimeConstants.SUNDAY).withTimeAtStartOfDay();

            input.setStartTime(dateFormatter(cal, currweekStart));
            input.setEndTime(dateFormatter(cal, currweekEnd));

        } else if (input.getFrequency().equals("MONTHLY")) {

            cal.setTime(format.parse(input.getStartDate()));
            DateTime prevMonth = new DateTime(cal);
            DateTime premonthStart = prevMonth.withDayOfMonth(1).withTimeAtStartOfDay();
            input.setPrevStartTime(dateFormatter(cal, premonthStart));

            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.DATE, -1);
            DateTime prevmonthEnd = new DateTime(cal);
            input.setPrevEndTime(dateFormatter(cal, prevmonthEnd));


            cal.setTime(format.parse(input.getEndDate()));
            DateTime currMonth = new DateTime(cal);
            DateTime currmonthStart = currMonth.withDayOfMonth(1).withTimeAtStartOfDay();
            input.setStartTime(dateFormatter(cal, currmonthStart));

            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.DATE, -1);
            DateTime currmonthEnd = new DateTime(cal);
            input.setEndTime(dateFormatter(cal, currmonthEnd));
        }
    }

			
//		} else if (input.getFrequency().equals("QUARTERLY")) {
//
//			cal.setTime(format.parse(input.getEndDate()));
//			String endDate = input.getEndDate();
//
//			int mtid = Integer.parseInt(endDate.substring(4,6));//01...04...06...12
//			int remainder = mtid%3; // 0 or 1 or 2
//
//			String currMonth;
//			String endMonth;
//			String startMonth;
//
//			if(remainder==1){
//				currMonth=endDate.substring(0,6);
//				endMonth=currMonth;
//				startMonth=currMonth;
//			} else if (remainder==2){
//				currMonth=endDate.substring(0,6);
//				cal.add(Calendar.MONTH,-1);
//				endMonth=monthFormat.format(cal.getTime());
//				startMonth=endMonth;
//			} else {//remainder==0
//				currMonth=endDate.substring(0,6);
//				cal.add(Calendar.MONTH,-1);
//				endMonth=monthFormat.format(cal.getTime());
//				cal.add(Calendar.MONTH,-1);
//				startMonth=monthFormat.format(cal.getTime());
//			}
//
//			input.setCurrDate(currMonth);
//			input.setStartDate(startMonth);
//			input.setEndDate(endMonth);
//
//		} else if (input.getFrequency().equals("YEARLY")) {
//			cal.setTime(format.parse(input.getEndDate()));
//			String endDate = input.getEndDate();
//
//			int mtid = Integer.parseInt(endDate.substring(4,6));//01...04...06...12
//			int remainder = mtid%12; // 0,1..11
//
//			String currMonth;
//			String endMonth;
//			String startMonth;
//
//			if(remainder==1){
//				currMonth=endDate.substring(0,6);
//				endMonth=currMonth;
//				startMonth=currMonth;
//			} else if(remainder==0){
//				currMonth=endDate.substring(0,6);
//				cal.add(Calendar.MONTH,-1);
//				endMonth=monthFormat.format(cal.getTime());
//				remainder=10;
//				cal.add(Calendar.MONTH,-remainder);
//				startMonth=monthFormat.format(cal.getTime());
//			} else {
//				currMonth=endDate.substring(0,6);
//				cal.add(Calendar.MONTH,-1);
//				endMonth=monthFormat.format(cal.getTime());
//				remainder=remainder-2;
//				cal.add(Calendar.MONTH,-remainder);
//				startMonth=monthFormat.format(cal.getTime());
//			}
//
//			input.setCurrDate(currMonth);
//			input.setStartDate(startMonth);
//			input.setEndDate(endMonth);
//		}
//	}

	
	/*public static void setCrousalDates(AudienceDataParameters input) throws Exception{
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		DateFormat monthFormat = new SimpleDateFormat("yyyyMM");
		
		Calendar cal = Calendar.getInstance();
		
         if(input.getFrequency().equals("DAILY")){
			
			input.setStartTime(input.getStartDate());
			input.setEndTime(input.getEndDate());
		}
         else if(input.getFrequency().equals("WEEKLY")){
			
			String startWeekId = createWeekId(input.getStartDate());
			String endWeekId = createWeekId(input.getEndDate());
			
			System.out.print("WeekId : --"+startWeekId +"--"+endWeekId);
			
			input.setStartTime(startWeekId);
			input.setEndTime(endWeekId);
			
		} else if(input.getFrequency().equals("MONTHLY")){
			
			cal.setTime(format.parse(input.getStartDate()));
			String startMonthId = monthFormat.format(cal.getTime());
			
			cal.setTime(format.parse(input.getEndDate()));
			String endMonthId = monthFormat.format(cal.getTime());

			input.setStartTime(startMonthId);
			input.setEndTime(endMonthId);
			
			
		} else if (input.getFrequency().equals("QUARTERLY")) {
			
			cal.setTime(format.parse(input.getEndDate()));
			String endDate = input.getEndDate();
			
			int mtid = Integer.parseInt(endDate.substring(4,6));//01...04...06...12
			int remainder = mtid%3; // 0 or 1 or 2
			
			String currMonth;
			String endMonth;
			String startMonth;
			
			if(remainder==1){
				currMonth=endDate.substring(0,6);
				endMonth=currMonth;
				startMonth=currMonth;
			} else if (remainder==2){
				currMonth=endDate.substring(0,6);
				cal.add(Calendar.MONTH,-1);
				endMonth=monthFormat.format(cal.getTime());
				startMonth=endMonth;
			} else {//remainder==0
				currMonth=endDate.substring(0,6);
				cal.add(Calendar.MONTH,-1);
				endMonth=monthFormat.format(cal.getTime());
				cal.add(Calendar.MONTH,-1);
				startMonth=monthFormat.format(cal.getTime());
			}
			
			input.setCurrDate(currMonth);
			input.setStartDate(startMonth);
			input.setEndDate(endMonth);
				
		} else if (input.getFrequency().equals("YEARLY")) {
			cal.setTime(format.parse(input.getEndDate()));
			String endDate = input.getEndDate();
			
			int mtid = Integer.parseInt(endDate.substring(4,6));//01...04...06...12
			int remainder = mtid%12; // 0,1..11
			
			String currMonth;
			String endMonth;
			String startMonth;
			
			if(remainder==1){
				currMonth=endDate.substring(0,6);
				endMonth=currMonth;
				startMonth=currMonth;
			} else if(remainder==0){
				currMonth=endDate.substring(0,6);
				cal.add(Calendar.MONTH,-1);
				endMonth=monthFormat.format(cal.getTime());
				remainder=10;
				cal.add(Calendar.MONTH,-remainder);
				startMonth=monthFormat.format(cal.getTime());
			} else {
				currMonth=endDate.substring(0,6);
				cal.add(Calendar.MONTH,-1);
				endMonth=monthFormat.format(cal.getTime());
				remainder=remainder-2;
				cal.add(Calendar.MONTH,-remainder);
				startMonth=monthFormat.format(cal.getTime());
			}
			
			input.setCurrDate(currMonth);
			input.setStartDate(startMonth);
			input.setEndDate(endMonth);
		}
	}*/
	
	/*public static void setDates(InputObject input) throws Exception{
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		DateFormat weekFormat = new SimpleDateFormat("yyyyww");
		DateFormat monthFormat = new SimpleDateFormat("yyyyMM");

		Calendar cal = Calendar.getInstance();


		if(input.getFrequency().equals("DAILY")){

			cal.setTime(format.parse(input.getEndDate()));

			cal.add(Calendar.DAY_OF_MONTH,-input.getGoBack());

			input.setStartDate(format.format(cal.getTime()));

		} else if (input.getFrequency().equals("WEEKLY")) {

			cal.setTime(format.parse(input.getEndDate()));
			//String endWeek = weekFormat.format(cal.getTime());
			String endWeek = createWeekId(input.getEndDate());

			cal.add(Calendar.WEEK_OF_YEAR, -input.getGoBack());

			//String startWeek = weekFormat.format(cal.getTime());
			String startWeekDate = format.format(cal.getTime());
			String startWeek = createWeekId(startWeekDate);

			input.setStartDate(startWeek);
			input.setEndDate(endWeek);

		} else if (input.getFrequency().equals("MONTHLY")) {

			cal.setTime(monthFormat.parse(input.getEndDate()));
			String endMonth = input.getEndDate();

			//String endMonth = monthFormat.format(cal.getTime());
			//TODO:make it to 1 to 12 instead of 0 to 11
			cal.add(Calendar.MONTH, -input.getGoBack());

			String startMonth = monthFormat.format(cal.getTime());

			input.setStartDate(startMonth);
			input.setEndDate(endMonth);
		} else if (input.getFrequency().equals("QUARTERLY")) {
			cal.setTime(monthFormat.parse(input.getEndDate()));
			String endMonth = input.getEndDate();
			int mtid = Integer.parseInt(endMonth.substring(4,6));//01...04...06...12
			int remainder = mtid%3; // 0 or 1 or 2
			//if(remainder==0)
				//remainder=3;

			cal.add(Calendar.MONTH, -remainder);
			String actualEndMonth = monthFormat.format(cal.getTime());
			System.out.println("actualEndMonth:"+actualEndMonth);
			int monthgoback = (input.getGoBack()*3);
			cal.add(Calendar.MONTH, -monthgoback);
			String startMonth = monthFormat.format(cal.getTime());

			String endQuarter = createQuarterId(actualEndMonth);
			String startQuarter = createQuarterId(startMonth);

			input.setStartDate(startQuarter);
			input.setEndDate(endQuarter);

		} else if (input.getFrequency().equals("YEARLY")) {
			cal.setTime(monthFormat.parse(input.getEndDate()));
			String endMonth = input.getEndDate();
			int yearid = Integer.parseInt(endMonth.substring(0,4));
			int endyearid;
			if(endMonth.substring(4, 6).equals("12")){
				endyearid = yearid;
			} else {
				endyearid = yearid-1;
			}
			int startyearid = endyearid-input.getGoBack();
			input.setStartDate(Integer.toString(startyearid));
			input.setEndDate(Integer.toString(endyearid));
		}

	}
	
    public static String createEndDate(String dateid, String freq) throws Exception{
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            DateFormat monthFormat = new SimpleDateFormat("yyyyMM");
            
            Calendar cal = Calendar.getInstance();
            
            
            if(freq.equalsIgnoreCase("WEEKLY")){
               // cal.setTime(format.parse(dateid));
                
                
               // dateid.substring(4);
                
                cal.set(Calendar.YEAR, Integer.parseInt(dateid.substring(0,4)));
                DateTime currWeek = new DateTime(cal);
               // cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(dateid.substring(4)));
             
                DateTime currweekEnd = currWeek.withWeekOfWeekyear(Integer.parseInt(dateid.substring(4))).withDayOfWeek(DateTimeConstants.SUNDAY).withTimeAtStartOfDay();
              
                return dateFormatter(cal, currweekEnd);
            }else{//Monthly
                cal.setTime(monthFormat.parse(dateid));
                cal.add(Calendar.MONTH, 1);  
                cal.set(Calendar.DAY_OF_MONTH, 1);  
                cal.add(Calendar.DATE, -1);  
	        DateTime currmonthEnd = new DateTime( cal );
                return (dateFormatter(cal,currmonthEnd));
            }
            
        }*/
    
//	public static String createWeekId(String dateid) throws Exception{
//		DateFormat format = new SimpleDateFormat("yyyyMMdd");
//
//		Calendar cal = Calendar.getInstance();
//
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
//		cal.setMinimalDaysInFirstWeek(4);
//		cal.setTime(format.parse(dateid));
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		int week = cal.get(Calendar.WEEK_OF_YEAR);
//		String weekid;
//		if(month==11 && week==1){
//			year=year+1;
//		} else if (month==0 && week==52){
//			year=year-1;
//		}
//		if(week<10)
//			weekid=year+"0"+week;
//		else
//			weekid=year+""+week;
//
//		return weekid;
//	}
	
	/*private static String createQuarterId(String monthid){
		int mtid = Integer.parseInt(monthid.substring(4,6));
		String yrid = monthid.substring(0,4);
		
		int qtr = 0;
		if(mtid>0 && mtid <4){
			qtr=1;
		} else if (mtid>=4 && mtid <7){
			qtr=2;
		} else if (mtid>=7 && mtid <10){
			qtr=3;
		} else if (mtid>=10 && mtid <13){
			qtr=4;
		} 
		
		String quarter=yrid+qtr;
		return quarter;
	}*/
	

	
	
//	public static void setDates(InputObject input) throws Exception{
//		DateFormat format = new SimpleDateFormat("yyyyMMdd");
//		DateFormat monthFormat = new SimpleDateFormat("yyyyMM");
//
//		Calendar cal = Calendar.getInstance();
//		if(input.getFrequency().equals("DAILY")){
//
//
//			input.setStartTime(input.getStartDate());
//			input.setEndTime(input.getEndDate());
//		}
//		else if(input.getFrequency().equals("WEEKLY")){
//
//			String startWeekId = createWeekId(input.getStartDate());
//			String endWeekId = createWeekId(input.getEndDate());
//
//			input.setStartTime(startWeekId);
//			input.setEndTime(endWeekId);
//
//
//
//		} else if(input.getFrequency().equals("MONTHLY")){
//
//			cal.setTime(format.parse(input.getStartDate()));
//			String startMonthId = monthFormat.format(cal.getTime());
//
//			cal.setTime(format.parse(input.getEndDate()));
//			String endMonthId = monthFormat.format(cal.getTime());
//
//			input.setStartTime(startMonthId);
//			input.setEndTime(endMonthId);
//		}
//	}
//
//	public static void main(String args[]) throws Exception{
//
//	/*	String startDate="20140815";
//		String endDate="20140924";
//
//		AudienceDataParameters input=new AudienceDataParameters ();
//		input.setStartDate(startDate);
//		input.setEndDate(endDate);
//		input.setFrequency("MONTHLY");*/
//		//QueryDateUtil.createEndDate("2014014","WEEKLY");
//		/*AudienceDataParameters input=new AudienceDataParameters ();
//		input.setStartDate("20140902");
//		input.setEndDate("20140909");
//	    input.setFrequency("MONTHLY");
//
//		QueryDateUtil.setDates(input);
//
//		System.out.print(""+input.getStartTime());
//		System.out.print(""+input.getEndTime());
//
//		 input.setStartDate("20140902");
//			input.setEndDate("20140909");
//		 input.setFrequency("WEEKLY");
//
//			QueryDateUtil.setDates(input);
//
//			System.out.print("\n"+input.getStartTime());
//			System.out.print("\n"+input.getEndTime());*/
//
//	}

}
