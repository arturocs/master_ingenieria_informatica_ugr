package com.arturo.api;


import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.arturo.dao.ProductosDAO;
import com.arturo.dao.UsuariosDAO;
import com.arturo.models.Producto;
import com.arturo.models.Usuario;

@Path(value = "/ordenadores")
public class Rutas {

	String msg = null;

	@GET
	@Produces("application/json")
	@Path(value = "/")
	public Response getData() {
		List<Producto> msg = new ProductosDAO().getProductos();
		return Response.status(Status.OK).entity(msg).build();
	}

	@DELETE
	@Path("/catalogo/{id}")
	@Consumes("application/json")
	public Response borrarProducto(@PathParam("id") String Id) {
		ProductosDAO sdao = new ProductosDAO();
		sdao.eliminar(Long.parseLong(Id));
		return Response.ok().build();
	}

	@POST
	@Path("/")
	@Consumes("application/json")
	public Response aniadir_producto(Producto p) {
		ProductosDAO sdao = new ProductosDAO();
		sdao.guardar(p);
		return Response.ok().entity(p).build();
	}

	@POST
	@Path("/registrar")
	@Consumes("application/json")
	public Response createUser(Usuario usuario) {

		UsuariosDAO dao = new UsuariosDAO();
		boolean result = dao.registrar(usuario.getEmail(), usuario.getNombre(), usuario.getApellido(),
				usuario.getContrasena());
		if (result) {
			return Response.ok().build();
		} else {
			return Response.notModified().build();
		}
	}

	@POST
	@Path("/iniciar_sesion")
	@Consumes("application/json")
	public Response iniciarSesion(Usuario usuario) {
		if (usuario.getEmail().equals("admin") && usuario.getContrasena().equals("admin")) {
			return Response.status(301).build();
		} else {
			UsuariosDAO dao = new UsuariosDAO();
			Usuario u = dao.iniciar_sesion(usuario.getEmail(), usuario.getContrasena());
			if (u != null) {
				return Response.ok().build();
			} else {
				return Response.status(401).build();
			}
		}
	}

	@GET
	@Path("/carrito/{email}")
	@Produces("application/json")
	public Response getCarrito(@PathParam("email") String email) {
		UsuariosDAO udao = new UsuariosDAO();
		List<Producto> l = udao.detallesCarrito(email);
		return Response.ok().entity(l).build();
	}

	@DELETE
	@Path("/carrito/{email}/{id}")
	@Consumes("application/json")
	public Response borrarDelCarrito(@PathParam("email") String email, @PathParam("id") String id) {

		UsuariosDAO dao = new UsuariosDAO();
		Usuario u = dao.buscar(email);
		if (u != null) {
			dao.borrarDelCarrito(Long.parseLong(id), email);
			return Response.ok().build();
		} else {
			return Response.notModified().build();
		}
	}

	@POST
	@Path("/comprar/{id}")
	@Consumes("application/json")
	public Response comprar(Usuario usuario, @PathParam("id") String id) {

		UsuariosDAO dao = new UsuariosDAO();
		Usuario u = dao.iniciar_sesion(usuario.getEmail(), usuario.getContrasena());

		if (u != null) {
			dao.comprar(Long.parseLong(id), u);
			return Response.ok().entity(id).build();
		} else {
			return Response.noContent().build();
		}
	}

}
