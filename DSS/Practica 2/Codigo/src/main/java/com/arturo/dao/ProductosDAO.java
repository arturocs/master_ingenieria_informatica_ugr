package com.arturo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.arturo.models.JPAUtil;
import com.arturo.models.Producto;


public class ProductosDAO {

	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	public void guardar(Producto s) {
		entity.getTransaction().begin();
		entity.persist(s);
		entity.getTransaction().commit();
	}

	public void editar(Producto s) {
		entity.getTransaction().begin();
		entity.merge(s);
		entity.getTransaction().commit();
	}


	public Producto buscar(Long id) {
		Producto s = new Producto();
		s = entity.find(Producto.class, id);
		return s;
	}


	public void eliminar(Long id) {
		Producto s = new Producto();
		s = entity.find(Producto.class, id);
		entity.getTransaction().begin();
		entity.remove(s);
		entity.getTransaction().commit();
	}


	public List<Producto> getProductos() {
		List<Producto> productos = new ArrayList<>();
		Query query = entity.createQuery("SELECT p FROM Producto p");
		productos = query.getResultList();
		return productos;
	}

}
