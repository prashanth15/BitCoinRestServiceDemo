package com.websystique.springmvc.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.dao.BitCoinDAO;
import com.websystique.springmvc.domain.Decision;

@RestController
public class BitCoinRestController {
	
	@Autowired
	private BitCoinDAO bitCoinDAO;

	
	@RequestMapping("/data/custom")
	@ResponseBody
	public List<String> getAllList(@RequestParam("strDate") String strDate, 
			@RequestParam("endDate") String endDate) throws IOException,ParseException{
		return bitCoinDAO.listCustom(strDate,endDate);
	}
	
	@RequestMapping("/data/period")
	@ResponseBody
	public List<String> getListlastDuration(@RequestParam("duration") String duration) 
			throws IOException,ParseException{
		return bitCoinDAO.listlastDuration(duration);
	}
	
	@RequestMapping("/data/rollingAverage")
	@ResponseBody
	public Queue<Double> getRollingAverage(@RequestParam("strDate") String strDate, 
			@RequestParam("endDate") String endDate) throws IOException,ParseException {
		return bitCoinDAO.getRollingAverage(strDate,endDate);
	}
	
	@RequestMapping("/data/decision")
	@ResponseBody
	public Decision getDecision() throws IOException,ParseException{
		return bitCoinDAO.getDecision();
	}
}
