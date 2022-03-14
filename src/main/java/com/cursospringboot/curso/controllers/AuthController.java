package com.cursospringboot.curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.curso.dao.UsuarioDao;
import com.cursospringboot.curso.models.Usuario;

@RestController
public class AuthController {
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@RequestMapping(value="api/login", method = RequestMethod.POST)
	public String iniciarSesion(@RequestBody Usuario usuario) {		
		return usuarioDao.verificarCredenciales(usuario)?"OK":"FAIL";
	}
}
