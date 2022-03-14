package com.cursospringboot.curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.curso.dao.UsuarioDao;
import com.cursospringboot.curso.models.Usuario;
import com.cursospringboot.curso.utils.JWTUtil;

@RestController
public class AuthController {
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@RequestMapping(value="api/login", method = RequestMethod.POST)
	public String iniciarSesion(@RequestBody Usuario usuario) {		
		Usuario user = usuarioDao.getUserByCredentials(usuario);
		if(user!=null) {
			String tokenJWT = jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
			return tokenJWT;
		}
		return "FALSE";
	}
}
