package com.epam.services;

import com.epam.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private final UserDAO dao;

	@Autowired
	public CustomUserDetailsService(UserDAO dao) {
		this.dao = dao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return dao.getUser(username);
	}

}
