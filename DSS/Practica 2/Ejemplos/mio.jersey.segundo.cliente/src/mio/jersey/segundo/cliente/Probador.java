package mio.jersey.segundo.cliente;
import java.net.URI;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Probador {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientConfig config = new DefaultClientConfig();
		Client cliente = Client.create(config);
		WebResource servicio = cliente.resource(getBaseURI());
		System.out.println("Mostrar contenido del recurso para aplicacion XML");
		System.out.println(servicio.path("rest").
		path("todos").accept(MediaType.TEXT_XML).get(String.class));
		
	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/mio.jersey.segundo").build();
	}

}
