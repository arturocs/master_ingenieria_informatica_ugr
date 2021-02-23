package jpa.familias.main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpa.familias.modelo.Familia;
import jpa.familias.modelo.Persona;


import java.util.List;

public class JpaTest {
	private static final String PERSISTENCE_UNIT_NAME = "gente";
	private EntityManagerFactory factoria;
	@Before
	public void setUp() throws Exception {
		factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factoria.createEntityManager();
        //Comenzar una nueva transaccion de tal forma que podamos hacer persistente una nueva entidad
        em.getTransaction().begin();
        //leer las entradas existentes
        Query q = em.createQuery("select m from Persona m");
        // En este punto Personas debe estar vacio
        // tenemos entradas?
        boolean createNewEntries = (q.getResultList().size() == 0);
        // No, pues vamos a crearnos nuevas entradas
        if (createNewEntries) {
            assertTrue(q.getResultList().size() == 0);
            Familia familia = new Familia();
            familia.setDescripcion("Familia de los Martinez");
            em.persist(familia);
            for (int i = 0; i < 40; i++) {
                Persona persona = new Persona();
                persona.setNombre("Jaime_" + i);
                persona.setApellido("Martinez_" + i);
                em.persist(persona);
                // ahora que persista la relacion entre familia y persona
                familia.getMiembros().add(persona);
                em.persist(persona);
                em.persist(familia);
            }
        }
        // Comprometer (commit) la transaccion, lo que ocasionara que la entidad sea almacenada en la base de datos 
        em.getTransaction().commit();
        //Siempre hay que cerrar el EntityManager para que se guarden los recursos 
        em.close();
	}

	@Test
    public void comprobarGenteDisponible() {
    	//Ahora vamos a comprobar la base de datos y ver si las entradas creadas estan alli
    	//crer un nuevo EntityManager "fresco"
        EntityManager em = factoria.createEntityManager();
        //Realizar una consulta simple a todas las entidades Message
        Query q = em.createQuery("select m from Persona m");
        //Debemos tener 40 personas en la base de datos      
        assertTrue(q.getResultList().size() == 39);
        em.close();
    }
    @Test
    public void comprobarFamilia() {
        EntityManager em = factoria.createEntityManager();
        // Recorrer cada una de las entidades e imprimir cada uno de sus mensajes,
        //asi como la fecha de creacion
        Query q = em.createQuery("select f from Familia f");
        //deberiamos tener una sola familia con 40 personas
        assertTrue(q.getResultList().size() == 1);
        assertTrue(((Familia) q.getSingleResult()).getMiembros().size() == 40);
        em.close();
    }
    @Test(expected = javax.persistence.NoResultException.class)
    public void eliminarPersona() {
        EntityManager em = factoria.createEntityManager();
        // Comenzar una nueva transaccioin local de tal forma que podamos hacer persistir una nueva entidad
        em.getTransaction().begin();
        Query q = em
                .createQuery("SELECT p FROM Persona p WHERE p.nombre = :nombre AND p.apellido = :apellido");
        q.setParameter("nombre", "Jaime_1");
        q.setParameter("apellido", "Martinez_1");
        Persona usuario = (Persona) q.getSingleResult();
        em.remove(usuario);
        em.getTransaction().commit();
        Persona persona = (Persona) q.getSingleResult();
        em.close();
    }

}
