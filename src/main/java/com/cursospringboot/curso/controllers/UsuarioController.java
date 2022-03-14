package com.cursospringboot.curso.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursospringboot.curso.criptographia.Encriptado;
import com.cursospringboot.curso.dao.UsuarioDao;
import com.cursospringboot.curso.models.Usuario;
import com.cursospringboot.curso.utils.JWTUtil;

@RestController
public class UsuarioController {
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	JWTUtil jwtUtil;
	
	public boolean validarToken(String token) {
		String userId = jwtUtil.getKey(token);
		return userId!=null;
	}
	
	@RequestMapping(value="api/usuarios/{id}", method = RequestMethod.GET)
	public Usuario getUsuario(@PathVariable Long id,@RequestHeader(value="Autorization") String token) {		
		if(!validarToken(token))
			return null;
		return usuarioDao.getUsuario(id);		
	}
	
	@RequestMapping(value="api/usuarios", method = RequestMethod.GET)
	public List<Usuario> getUsuarios(@RequestHeader(value="Autorization") String token) {		
		if(!validarToken(token))
			return null;
		return usuarioDao.getUsuarios();		
	}
	
	@RequestMapping(value="api/usuarios/{id}", method = RequestMethod.DELETE)
	public void eliminarUsuario(@PathVariable Long id,@RequestHeader(value="Autorization") String token) {		
		if(!validarToken(token))
			return;
		usuarioDao.delete(id);		
	}
	
	@RequestMapping(value="api/usuarios", method = RequestMethod.POST)
	public void registrarUsuario(@RequestBody Usuario usuario) {		
		usuario.setPassword(Encriptado.getEncriptadoForte(usuario.getPassword()));
		usuarioDao.register(usuario);	
	}
	
}
