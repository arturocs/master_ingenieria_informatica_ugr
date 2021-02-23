package mio.jersey.primero.cliente;
import java.net.URI;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
public class Test {

	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(getBaseURI());
		System.out.println("Mostrar contenido del recurso para aplicacion XML");
		System.out.println(servicio.path("rest").
		path("todo").accept(MediaType.APPLICATION_XML).get(String.class));
	}
	private static URI getBaseURI(){
		return UriBuilder.fromUri("http://localhost:8080/mio.jersey.primero").build();
	}
}
