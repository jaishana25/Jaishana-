package com.example.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Device;
import com.example.model.Devices;

@Controller
public class MainController {
	
	@Autowired
	private RestTemplate rs;
	
	@RequestMapping("/home")
	public String home() {
		return "home.html";
	}
	
	@RequestMapping("/apple")
	public ModelAndView apple() {
		ModelAndView mv = new ModelAndView();
		Devices deviceList = rs.getForObject("http://Apple/apple", Devices.class);
		mv.addObject("Apple", deviceList);
		return mv;
	}
	
	@RequestMapping("/samsung")
	public ModelAndView samsung() {
		ModelAndView mv = new ModelAndView();
		Devices deviceList = rs.getForObject("http://Samsung/samsung", Devices.class);
		mv.addObject("Samsung", deviceList);
		return mv;
		
	}
}
