package com.cursospringboot.curso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursospringboot.curso.criptographia.Encriptado;
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

	@Override
	public boolean verificarCredenciales(Usuario usuario) {
		/*POSIBLE VARIANTE
		 * String query = "FROM Usuario WHERE email = :email AND password = :password";
		List<Usuario> list = entityManager.createQuery(query)
				.setParameter("email", usuario.getEmail())
				.setParameter("password", usuario.getPassword())
				.getResultList();
		
		return !list.isEmpty(); //equivalente a list.isEmpty()?false:true;
		*/
		String query = "FROM Usuario WHERE email = :email";
		List<Usuario> list = entityManager.createQuery(query)
				.setParameter("email", usuario.getEmail())
				.getResultList();
		
		return !list.isEmpty() && list.get(0).getPassword().
				equals(Encriptado.getEncriptadoForte(usuario.getPassword()));
	}

}
