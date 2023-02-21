package com.example.restspringtemplate.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
	/**
	 * Gets the user by username to be used by Spring Security.
	 *
	 * @param username the username identifying the user whose data is required.
	 *
	 * @return the user details.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		// Todo: implement this method

		return new org.springframework.security.core.userdetails.User(
			"REPLACE_ME_WITH_USERNAME",
			"REPLACE_ME_WITH_PASSWORD_HASH",
			List.of(new SimpleGrantedAuthority("REPLACE_ME_WITH_USER_ROLE"))
		);
	}
}
