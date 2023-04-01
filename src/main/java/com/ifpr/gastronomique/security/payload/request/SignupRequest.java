package com.ifpr.gastronomique.security.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

	@NotBlank
	@Size(min = 2, max = 100)
	private String fullName;
	
	private boolean isActive;
	
	@Size(min = 3, max = 100)
	private String username;
	
	@NotBlank
	@Size(max = 100)
	@Email
	private String email;
	
	private Set<String> role;
	
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
}
