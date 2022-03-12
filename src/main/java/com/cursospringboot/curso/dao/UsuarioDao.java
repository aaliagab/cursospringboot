package com.cursospringboot.curso.dao;

import java.util.List;

import com.cursospringboot.curso.models.Usuario;

public interface UsuarioDao {
	List<Usuario> getUsuarios();
	Usuario getUsuario(Long id);
	void delete(Long id);
	void register(Usuario usuario);
}
