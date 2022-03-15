package com.cursospringboot.curso.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

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
	
	@RequestMapping(value="api/usuarios/pdf", method = RequestMethod.GET)
	public void pdfUsuario(@RequestHeader(value="Autorization") String token) {		
		if(validarToken(token)){
			List<Usuario> objs = usuarioDao.getUsuarios();
                       
            JRBeanCollectionDataSource jrbdatasource = new JRBeanCollectionDataSource(objs);
            try {
				JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reportes/usuarios.jrxml"));
				Map<String, Object> parametros = new HashMap<>();
                //parametros.put("logo", this.getClass().getResource("/resource/logo.png").toString());
				JasperPrint jprint = JasperFillManager.fillReport(compileReport, parametros, jrbdatasource);
				//JasperExportManager.exportReportToPdfFile(jprint, "usuarios.pdf");
				JasperViewer jviewer = new JasperViewer(jprint, false);
                jviewer.setVisible(true);
                jviewer.setTitle("usuarios.pdf");
			} catch (FileNotFoundException | JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}		
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
