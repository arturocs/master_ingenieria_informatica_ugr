package mio.jersey.segundo.maven.recurso;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import mio.jersey.segundo.maven.modelo.Todo;
import mio.jersey.segundo.maven.modelo.TodoDao;

public class TodoRecurso {
	@Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;
 
    public TodoRecurso(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }
  //para integracion con las aplicaciones
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Todo getTodo() {
        Todo todo = TodoDao.INSTANCE.getModel().get(id);
        if(todo==null)
            throw new RuntimeException("Get: Todo con identificador " + id +  " no se encuentra");
        return todo;
    }

    // para integracion con navegadores
    @GET
    @Produces(MediaType.TEXT_XML)
    public Todo getTodoHTML() {
        Todo todo = TodoDao.INSTANCE.getModel().get(id);
        if(todo==null)
            throw new RuntimeException("Get: Todo con identificador " + id +  " no se encuentra");
        return todo;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response putTodo(JAXBElement<Todo> todo) {
        Todo c = todo.getValue();
        return putAndGetResponse(c);
    }

    @DELETE
    public void deleteTodo() {
        Todo c = TodoDao.INSTANCE.getModel().remove(id);
        if(c==null)
            throw new RuntimeException("Delete: Todo con identificador " + id +  " no se encuentra");
    }
    private Response putAndGetResponse(Todo todo) {
        Response res;
        if(TodoDao.INSTANCE.getModel().containsKey(todo.getId())) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        TodoDao.INSTANCE.getModel().put(todo.getId(), todo);
        return res;
    }
}