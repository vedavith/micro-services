package com.auth.service.controllers;

import java.net.http.HttpResponse;
import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping(path = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")

public class TokenController {

	@Value("${auth0.audience}")
	private String audience;

	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	private String url;

	@Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
	private String clientId;
	
	@Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
	private String ClientSecret;
	
	 @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Object> getToken() throws UnirestException {
		 
		 // Mapping object
		 Map<String, String> parameters = new HashMap<>();
		 parameters.put("client_id", this.clientId);
		 parameters.put("client_secret", this.ClientSecret);
		 parameters.put("audience", this.audience);
		 parameters.put("grant_type", "client_credentials");

		 JSONObject body = new JSONObject(parameters); 
		 		 
		 com.mashape.unirest.http.HttpResponse<String> response = Unirest.post(this.url)
				 .header("content-type", MediaType.APPLICATION_JSON_VALUE)
				 .body(body)
				 .asString();
		 return ResponseEntity.ok(response.getBody());
	 }
}
