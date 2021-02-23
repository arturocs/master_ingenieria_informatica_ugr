package controlador;


import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

enum Estado {
	ENCENDIDO, APAGADO, ACELERANDO
}

@ManagedBean(name = "controlador", eager = true)
@SessionScoped
public class Controlador {
	private Estado estado;

	public Controlador() {
		estado = Estado.APAGADO;
		System.out.println("Se inicia Controlador");
	}

	public String getEstado() {
		return estado.toString();
	}

	public String getBoton() {
		return switch (estado) {
		case ACELERANDO, APAGADO -> "ENCENDER";
		case ENCENDIDO -> "APAGAR";
		};

	}

	public void encender(ActionEvent event) {
		switch (estado) {
		case ENCENDIDO, ACELERANDO -> estado = Estado.APAGADO;
		case APAGADO -> estado = Estado.ENCENDIDO;
		}
	}

	public void acelerar(ActionEvent event) {
		estado = estado == Estado.ENCENDIDO ? Estado.ACELERANDO : estado;
	}
}
