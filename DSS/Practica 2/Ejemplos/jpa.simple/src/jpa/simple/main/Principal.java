package jpa.simple.main;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpa.simple.modelo.Completo;
import jpa.simple.modelo.Todo;
public class Principal {
	private static final String PERSISTENCE_UNIT_NAME = "tutorialJPA";
	private static EntityManagerFactory factoria;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factoria.createEntityManager();
		// leer las entradas existentes y escribir en la consola
		Query q = em.createQuery("select t from Completo t");
		//Crearse una lista con template: "Completo" a la que asignameros el resultado de la consulta
		//en la base de datos ("q.getResultList()"
		//Iterar en la lista e imprimir las instancias "completo"
		//Ahora imprimimos el numero de registros que tiene ya la base de datos
		List<Completo> listaCompleto = q.getResultList();
        for (Completo completo : listaCompleto) {
            System.out.println(completo);
        }
		
		System.out.println("Tamano: " + listaCompleto.size());
		
		//Ahora vamos a trabajar con una transaccion en la base de datos
		em.getTransaction().begin();
		//Crearse una instancia de completo y utilizar los metodos "setResumen()" y "setDescripcion()"
		//Posteriormente hay que decir al gestor de entidad (em) que la instancia va a ser persistente;
		//conseguir la transaccion ("em.getTransaction()") y hacerla definitiva ("commit()")
	
		//Por ultimo, hay que cerrar al gestor de entidad
		Completo completo = new Completo();
        completo.setResumen("Esto es una prueba");
        completo.setDescripcion("Esto es una prueba");
        em.persist(completo);
        em.getTransaction().commit();
		em.close();
	}

}
