package com.cursospringboot.curso.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.curso.dao.UsuarioDao;
import com.cursospringboot.curso.models.Usuario;

@RestController
public class UsuarioController {
	@Autowired
	UsuarioDao usuarioDao;
	
	@RequestMapping(value="api/usuarios/{id}", method = RequestMethod.GET)
	public Usuario getUsuario(@PathVariable Long id) {
		return usuarioDao.getUsuario(id);		
	}
	
	@RequestMapping(value="api/usuarios", method = RequestMethod.GET)
	public List<Usuario> getUsuarios() {		
		return usuarioDao.getUsuarios();		
	}
	
	@RequestMapping(value="api/usuarios/{id}", method = RequestMethod.DELETE)
	public void eliminarUsuario(@PathVariable Long id) {
		usuarioDao.delete(id);		
	}
	
	@RequestMapping(value="api/usuarios", method = RequestMethod.POST)
	public void registrarUsuario(@RequestBody Usuario usuario) {		
		usuarioDao.register(usuario);		
	}
	
	
}
