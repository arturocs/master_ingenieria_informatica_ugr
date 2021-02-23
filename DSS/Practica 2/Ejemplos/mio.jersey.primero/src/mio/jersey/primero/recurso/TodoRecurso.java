package mio.jersey.primero.recurso;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mio.jersey.primero.modelo.*;
@Path("/todo")
public class TodoRecurso {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Todo getXML() {
		Todo todo = new Todo();
		todo.setResumen("Este es mi primer todo");
		todo.setDescripcion("Este es mi primer todo");
		return todo;
	}
	@Produces({ MediaType.TEXT_XML })
	public Todo getHTML() {
		Todo todo = new Todo();
		todo.setResumen("Este es mi primer todo");
		todo.setDescripcion("Este es mi primer todo");
		return todo;
	}
}
