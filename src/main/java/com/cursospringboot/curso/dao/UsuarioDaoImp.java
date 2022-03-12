package com.cursospringboot.curso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursospringboot.curso.models.Usuario;
@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Usuario> getUsuarios() {
		return entityManager.createQuery("FROM Usuario").getResultList();
	}

	@Override
	public Usuario getUsuario(Long id) {
		return entityManager.find(Usuario.class, id);
	}

	@Override
	public void delete(Long id) {
		Usuario usuario = entityManager.find(Usuario.class, id);
		entityManager.remove(usuario);
	}

	@Override
	public void register(Usuario usuario) {
		entityManager.merge(usuario);
	}

}
