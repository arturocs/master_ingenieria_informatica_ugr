package com.arturo.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "email")
	private String email;

	@Column
	private String nombre;

	@Column
	private String apellido;

	@Column
	private String contrasena;

	@Column
	private Long carrito[];


	public Long[] getCarrito() {
		return carrito;
	}

	public void setCarrito(Long[] carrito) {
		this.carrito = carrito;
	}

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", contraseña=" + contrasena+"]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
