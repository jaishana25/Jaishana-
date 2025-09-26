package com.example.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Device;
import com.example.model.Devices;

@RestController
public class MainController {
	
	@RequestMapping("/samsung")
	public Devices samsung() {
		Device d1 = new Device(1, "Galaxy S23 Ultra", "Smart Phone -- from 1");
		Device d2 = new Device(2, "Galaxy S24 Ultra", "Smart Phone bad choice");
		Device d3 = new Device(3, "Galaxy S25 Ultra", "Smart Phone with AI");
		ArrayList<Device> devices = new ArrayList<>();
		devices.add(d1);
		devices.add(d2);
		devices.add(d3);
		Devices deviceList = new Devices(devices, "Samsung");
		return deviceList;
		
	}
}
