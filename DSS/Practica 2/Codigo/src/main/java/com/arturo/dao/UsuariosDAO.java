package com.arturo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import com.arturo.models.Usuario;
import com.arturo.models.JPAUtil;
import com.arturo.models.Producto;



public class UsuariosDAO {

	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	public void crear(Usuario u) {
		entity.getTransaction().begin();
		entity.persist(u);
		entity.getTransaction().commit();
	}

	public boolean registrar(String email, String nombre, String apellido, String contrasena) {
		Usuario c = new Usuario();
		if (entity.find(Usuario.class, email) == null) {
			Long carrito[]= new Long[]{};
			Usuario usuario = new Usuario();
			usuario.setEmail(email);
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setContrasena(contrasena);
			usuario.setCarrito(carrito);
			entity.getTransaction().begin();
			entity.persist(usuario);
			entity.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}

	public Usuario iniciar_sesion(String email, String contrasena) {
		Usuario c = new Usuario();
		c = entity.find(Usuario.class, email);
		if (c != null) {
			if (c.getContrasena().equals(contrasena)) {
				return c;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}


	
	public void comprar(Long idProducto, Usuario cliente) {
		Long c[]=cliente.getCarrito();
		if (c==null) {
			c= new Long[]{idProducto};
		}else {
			ArrayList<Long> carrito =  new ArrayList<Long>(Arrays.asList(c));
			carrito.add(idProducto);
			Long[] array = carrito.toArray(new Long[0]);
			cliente.setCarrito(array);
		}
					
		entity.getTransaction().begin();
		entity.merge(cliente);
		entity.getTransaction().commit();
	}
	
	public Usuario buscar(String email) {
		Usuario c = new Usuario();
		c = entity.find(Usuario.class, email);
		return c;
	}


	public List<Producto> detallesCarrito(String email) {
		Usuario c = new Usuario();
		c = entity.find(Usuario.class, email);
		if (c!=null) {
			List<Producto> ls = new ArrayList();
			ProductosDAO sd = new ProductosDAO();
			for (Long p : c.getCarrito()) {
				ls.add(sd.buscar(p));
			}
			return ls;
		}
		else {
			return null;
		}
	}
	
	public void borrarDelCarrito(Long idProducto, String email) {
		Usuario c = new Usuario();
		c = entity.find(Usuario.class, email);
		ArrayList<Long> carrito =  new ArrayList<Long>(Arrays.asList(c.getCarrito()));

		carrito.remove(idProducto);
		Long[] array = carrito.toArray(new Long[0]);
		c.setCarrito(array);

		entity.getTransaction().begin();
		entity.merge(c);
		entity.getTransaction().commit();
	}
	


}
