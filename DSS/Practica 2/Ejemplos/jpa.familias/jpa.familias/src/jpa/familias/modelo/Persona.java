package jpa.familias.modelo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
@Entity
public class Persona {
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String id;
    private String nombre;
    private String apellido;

    private Familia familia;

    private String nonsenseField = "";

    private List<Empleo> listaEmpleos = new ArrayList<Empleo>();
    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Leave the standard column name of the table
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    @ManyToOne
    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    @Transient
    public String getNonsenseField() {
        return nonsenseField;
    }

    public void setNonsenseField(String nonsenseField) {
        this.nonsenseField = nonsenseField;
    }

    @OneToMany
    public List<Empleo> getListaEmpleos() {
        return this.listaEmpleos;
    }

    public void setListaEmpleos(List<Empleo> nickName) {
        this.listaEmpleos = nickName;
    }

}
