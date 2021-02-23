package jpa.familias.modelo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empleo {
	 @Id
	    @GeneratedValue(strategy = GenerationType.TABLE)
	    private int id;
	    private double salario;
	    private String descrEmpleo;

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public double getSalario() {
	        return salario;
	    }

	    public void setSalario(double salario) {
	        this.salario = salario;
	    }

	    public String getDescrEmpleo() {
	        return descrEmpleo;
	    }

	    public void setDescrEmpleo(String descrEmpleo) {
	        this.descrEmpleo = descrEmpleo;
	    }

}
