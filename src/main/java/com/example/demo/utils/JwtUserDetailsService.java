package com.example.demo.utils;

import java.util.ArrayList;

import com.example.demo.Entitys.CustomerEntiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private ListUser listUser;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (listUser.getCustomers() != null) {
			User user = null;
			for (CustomerEntiry cus : listUser.getCustomers()) {
				if (cus.getUsername().equalsIgnoreCase(username)) {
					user = new User(username, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
							new ArrayList<>());
				}
			}

			if (user == null) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			} else {
				return user;
			}
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
