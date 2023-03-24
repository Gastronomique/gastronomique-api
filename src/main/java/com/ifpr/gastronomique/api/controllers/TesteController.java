package com.ifpr.gastronomique.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
	@GetMapping
	public String hello() {
		return "Hello World!".toUpperCase();
	}
	
}