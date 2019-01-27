package de.fhaachen.ipdashboard.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.fhaachen.ipdashboard.message.request.LoginForm;
import de.fhaachen.ipdashboard.message.response.JwtResponse;
import de.fhaachen.ipdashboard.security.CustomAuthenticationProvider;
import de.fhaachen.ipdashboard.security.jwt.JwtProvider;
import de.fhaachen.ipdashboard.security.services.UserDetailsServiceImpl;
import de.fhaachen.ipdashboard.security.services.UserPrinciple;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	/*@Autowired
	AuthenticationManager authenticationManager;*/

	@Autowired
	CustomAuthenticationProvider cauthProvider;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	UserDetailsServiceImpl userdetailsservice;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		/*Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));*/

		// Umleitung über den Provider wegen CustomAuthentification
		Authentication authentication = cauthProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);

		//UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		UserPrinciple currentUser = (UserPrinciple) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getUsername(), currentUser.getName(), currentUser.getAuthorities()));
	}
}