package prueba;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="holaMundo", eager=true)
@RequestScoped
public class HolaMundo {
	
	public HolaMundo(){
		System.out.println("Hola Mundo ha comenzado!");
	}
	public String getMessage(){
		return "Hola Mundo";
	}
}