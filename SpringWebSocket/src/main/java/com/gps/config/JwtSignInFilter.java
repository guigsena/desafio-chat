package com.gps.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gps.model.Usuario;

public class JwtSignInFilter extends AbstractAuthenticationProcessingFilter {


	public JwtSignInFilter(RequestMatcher requestMatcher, AuthenticationManager authManager) {
		super(requestMatcher);
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
		if (CorsUtils.isPreFlightRequest(req)) {
			res.setStatus(HttpServletResponse.SC_OK);
			return null;
		}

		if (!req.getMethod().equals(HttpMethod.POST.name())) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		Usuario creds = new ObjectMapper().readValue(
				req.getInputStream(),
				Usuario.class
				);

		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						creds.getEmail(),
						creds.getSenha()						
						)
				);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(res, auth.getName());
	}
}
