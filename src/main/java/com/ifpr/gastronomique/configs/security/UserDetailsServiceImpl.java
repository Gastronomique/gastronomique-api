package com.ifpr.gastronomique.configs.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ifpr.gastronomique.domain.models.UsuarioModel;
import com.ifpr.gastronomique.domain.repositories.UsuarioRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioModel usuarioModel = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> 
						new UsernameNotFoundException("Usuário não encontrado! usuário: " + username));
		
		return new User(usuarioModel.getUsername(),
				usuarioModel.getPassword(),
				true, true, true, true,
				usuarioModel.getAuthorities());
	}
}