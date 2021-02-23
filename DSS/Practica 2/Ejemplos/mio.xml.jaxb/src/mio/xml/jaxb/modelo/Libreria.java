package mio.xml.jaxb.modelo;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.mio.xml.jaxb.modelo")
public class Libreria {
	@XmlElementWrapper(name = "listaLibros")
	@XmlElement(name = "libro")
	private ArrayList<Libro> listaLibros;
	private String nombre;
	private String ubicacion;
	public void setListaLibros(ArrayList<Libro> listaLibros) {
		this.listaLibros = listaLibros;
	}
	public ArrayList<Libro> getListaDeLibros() {
		return listaLibros;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
}
