package com.example.demo.Controllers;

import com.example.demo.Entitys.CustomerEntiry;
import com.example.demo.utils.ListUser;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private ListUser listUser;

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public String firstPage() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		CustomerEntiry res = new CustomerEntiry();
		if (listUser.getCustomers() != null) {
			for (CustomerEntiry cus : listUser.getCustomers()) {
				if (cus.getUsername().equalsIgnoreCase(username)) {
					BeanUtils.copyProperties(cus, res);
					break;
				}
			}
		}
		return "Detail User : " + res.toString();
	}

	

}