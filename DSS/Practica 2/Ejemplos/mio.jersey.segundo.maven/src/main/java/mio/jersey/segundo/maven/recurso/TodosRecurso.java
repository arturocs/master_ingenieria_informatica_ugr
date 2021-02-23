package mio.jersey.segundo.maven.recurso;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.servlet.http.HttpServletResponse;
import mio.jersey.segundo.maven.modelo.Todo;
import mio.jersey.segundo.maven.modelo.TodoDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/todos")
public class TodosRecurso {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Todo> getTodosBrowser() {
		List<Todo> todos = new ArrayList<Todo>();
		todos.addAll(TodoDao.INSTANCE.getModel().values());
		return todos;
	}
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Todo> getTodos() {
		List<Todo> todos = new ArrayList<Todo>();
		todos.addAll(TodoDao.INSTANCE.getModel().values());
		return todos;
	}
	@GET
	@Path("cont")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int cont = TodoDao.INSTANCE.getModel().size();
		return String.valueOf(cont);
	}
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newTodo(@FormParam("id") String id,	@FormParam("resumen") String resumen, @FormParam("descripcion") String descripcion, @Context HttpServletResponse servletResponse) throws IOException {
		Todo todo = new Todo(id, resumen);
		if (descripcion != null) {
			todo.setDescripcion(descripcion);
		}
		TodoDao.INSTANCE.getModel().put(id, todo);
		servletResponse.sendRedirect("../crear_todo.html");
	}
	@Path("{todo}")
	public TodoRecurso getTodo(@PathParam("todo") String id) {
		return new TodoRecurso(uriInfo, request, id);
	}
}