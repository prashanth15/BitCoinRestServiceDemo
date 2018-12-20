package com.websystique.springmvc.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springmvc.domain.Data;
import com.websystique.springmvc.domain.Decision;
import com.websystique.springmvc.domain.Prices;


@Component
public class BitCoinDAO {	
	
	final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    Date start = null;
    Date end = null;
    
	public List<String> listCustom(String strDate, String endDate) throws IOException,ParseException{
		List<String> bitCoinPrices = new ArrayList<>();		
		Data data=initialiseJsonData();
        //start = dateFormat.parse("2018-12-01T00:00:00Z");
        //end = dateFormat.parse("2018-12-16T00:00:00Z");
        start = dateFormat.parse(strDate);
        end = dateFormat.parse(endDate);
				
				for (int i = 0; i < data.getData().getPrices().size(); i++) {
					Prices b = data.getData().getPrices().get(i);
					String time= b.getTime();					
					Date eachDate = dateFormat.parse(time);
			            if (eachDate.after(start) && eachDate.before(end)) {
			            	bitCoinPrices.add(b.getPrice());
			            	System.out.println("Time :"+time + " PRICE :"+b.getPrice() + "\n");
			            }
				}				
				
		return bitCoinPrices;
	}

	public List<String> listlastDuration(String lastDuration) throws IOException,ParseException{
		List<String> bitCoinPrices = new ArrayList<>();		
        Data data=initialiseJsonData();
        String formatEnd;
        String formatStart = dateFormat.format(new Date());
        start = dateFormat.parse(formatStart);
    	Calendar calStart = Calendar.getInstance();
    	calStart.setTime(start);
    	
        switch (lastDuration) {
        case "week":
        	calStart.add(Calendar.DATE, -7);
        	formatEnd = dateFormat.format(calStart.getTime());
            break;
        case "month":
        	calStart.add(Calendar.MONTH, -1);
        	formatEnd = dateFormat.format(calStart.getTime());
            break;
        case "year": 
        	calStart.add(Calendar.YEAR, -1);
        	formatEnd = dateFormat.format(calStart.getTime());
            break;
        default:
        	calStart.add(Calendar.DATE, -1);
        	formatEnd = dateFormat.format(calStart.getTime());
            break;
        }
        end = dateFormat.parse(formatEnd);
				
        for (int i = 0; i < data.getData().getPrices().size(); i++) {
			Prices b = data.getData().getPrices().get(i);
			String time= b.getTime();					
			Date eachDate = dateFormat.parse(time);
	            if (eachDate.after(end) && eachDate.before(start)) {
	            	bitCoinPrices.add(b.getPrice());
	            	System.out.println("Time :"+time + " PRICE :"+b.getPrice() + "\n");
	            }
		}				
		return bitCoinPrices;
	}

	public Queue<Double> getRollingAverage(String strDate, String endDate) throws IOException,ParseException{
		Data data=initialiseJsonData();
		LinkedList<Double> queue = new LinkedList<Double>();
        String avgStart = null;
        start = dateFormat.parse(strDate);
        end = dateFormat.parse(endDate);
		long diff = end.getTime() - start.getTime();		 
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		System.out.println("difference between days: " + diffDays);
	 
	    Calendar calStart = Calendar.getInstance();
    	calStart.setTime(start);
    	calStart.add(Calendar.DATE, -diffDays);
    	avgStart = dateFormat.format(calStart.getTime());
    	start = dateFormat.parse(avgStart);
    	System.out.println("start " + start);
    	System.out.println("end " + end);
	    
	    for (int i = 0; i < data.getData().getPrices().size(); i++) {
			Prices b = data.getData().getPrices().get(i);
			String time= b.getTime();
			Date eachDate = dateFormat.parse(time);		
			if (eachDate.after(start) && eachDate.before(end)) {
			queue.add(new Double(b.getPrice()));
			}
		}
	    System.out.println("queue " + queue);
	    LinkedList<Double> newQueue = new LinkedList<Double>();
	    
	    for (int i = 0; i < diffDays; i++) {
	    	double eachAvg=0.0;
	    	int sum=0;
            for(double j: queue){
                sum+=j;
            }
            eachAvg = (double)sum/diffDays;
            newQueue.offer(eachAvg);
            queue.pollLast();
		}	    
	    System.out.println("newQueue " + newQueue);

	    return newQueue;
	}

	public Decision getDecision() throws IOException,ParseException {
		Decision decisionObj = new Decision();
		String decision = "HOLD";		
        Data data=initialiseJsonData();
        double sum = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        String formatEnd;
        String formatStart = dateFormat.format(new Date());
        start = dateFormat.parse(formatStart);
    	Calendar calStart = Calendar.getInstance();
    	calStart.setTime(start);
    	calStart.add(Calendar.MONTH, -1);
    	formatEnd = dateFormat.format(calStart.getTime());
        end = dateFormat.parse(formatEnd);
        int count=0;
		
        for (int i = 0; i < data.getData().getPrices().size(); i++) {
			Prices b = data.getData().getPrices().get(i);
			String time= b.getTime();					
			Date eachDate = dateFormat.parse(time);
	            if (eachDate.after(end) && eachDate.before(start)) {
	            	double value = Double.parseDouble(b.getPrice());
	                if (value > max) max = value;
	                if (value < min) min = value;
	                sum += value;
	                count++;
	            	System.out.println("Time :"+time + " PRICE :"+b.getPrice() + "\n");
	            }
		}
        double average = sum / count;
        decisionObj.setMax(max);
        decisionObj.setMin(min);
        decisionObj.setAverage(average);
        
        if ((max-average)>(average-min) && Math.abs(max-average) >= 100){
        	decision = "BUY";
        } else if (Math.abs(max-average) <= 100) {
        	decision = "HOLD";
        } else {
        	decision = "SELL";
        }
        
        decisionObj.setDecision(decision);        
		return decisionObj;
	}
	
	private Data initialiseJsonData() throws IOException,ParseException{
		String fileName = "response.json";
		ClassLoader classLoader = new BitCoinDAO().getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte[] jsonData = Files.readAllBytes(file.toPath());
		ObjectMapper objectMapper = new ObjectMapper();
		Data data = objectMapper.readValue(jsonData, Data.class);
		return data;
		
	}
}