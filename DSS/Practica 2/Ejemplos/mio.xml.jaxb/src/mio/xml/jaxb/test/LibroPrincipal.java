package mio.xml.jaxb.test;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import mio.xml.jaxb.modelo.Libro;
import mio.xml.jaxb.modelo.Libreria;

public class LibroPrincipal {
	private static final String LIBRERIA_XML = "./libreria-jaxb.xml";
	public static void main(String[] args) throws JAXBException, IOException{
		// TODO Auto-generated method stub
		ArrayList<Libro> listaLibros = new ArrayList<Libro>();
		Libro libro1 = new Libro();
		libro1.setIsbn("978-0060554736");
		libro1.setNombre("El Juego");
		libro1.setAutor("Neil Strauss");
		libro1.setEditorial("Harpercollins");
		listaLibros.add(libro1);
		Libro libro2 = new Libro();
		libro2.setIsbn("978-3832180577");
		libro2.setNombre("Zonas humedas");
		libro2.setAutor("Charlotte Roche");
		libro2.setEditorial("Dumont Buchverlag");
		listaLibros.add(libro2);
		//Crear libreria
		Libreria libreria = new Libreria();
		libreria.setNombre("Frankfurt Bookstore");
		libreria.setUbicacion("Aeropuerto de Frankfurt");
		libreria.setListaLibros(listaLibros);
		// crear el contexto JAXB e instanciar el marshaller
		JAXBContext contexto = JAXBContext.newInstance(Libreria.class);
		Marshaller m = contexto.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// Escribir en la corriente de salida: System.out
		m.marshal(libreria, System.out);
		// Escribir en File
		m.marshal(libreria, new File(LIBRERIA_XML));
		// conseguir las variables de nuestro archivo XML, que hemos creado anteriormente
		System.out.println();
		System.out.println("Salida desde nuestro archivo XML: ");
		Unmarshaller um = contexto.createUnmarshaller();
		Libreria libreria2 = (Libreria) um.unmarshal(new FileReader(LIBRERIA_XML));
		ArrayList<Libro> lista = libreria2.getListaDeLibros();
	   //imprimir los libros de la libreria
		for (Libro libro : lista) {
		  System.out.println("Libro: " + libro.getNombre() + " de "+ libro.getAutor());
		}
	}
}