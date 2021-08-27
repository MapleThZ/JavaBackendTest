package com.example.demo.Controllers;

import java.util.Objects;

import com.example.demo.Models.ApiError;
import com.example.demo.Models.JwtRequest;
import com.example.demo.Models.JwtResponse;
import com.example.demo.Services.RegisterServiceImpl;
import com.example.demo.utils.JwtTokenUtil;
import com.example.demo.utils.JwtUserDetailsService;
import com.example.demo.utils.ListUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ListUser listUser;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private RegisterServiceImpl registerServiceImpl;

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		String status = registerServiceImpl.saveCustomer(authenticationRequest);
		if ("reject".equalsIgnoreCase(status)) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Salary less than 15000 bath");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		} else if ("duplicate".equalsIgnoreCase(status)) {
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "User is duplicate");
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String lastPage() {
		return listUser.getCustomers().toString();
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}