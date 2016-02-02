package ec.edu.epn.findclub.VO;

/**
 * Created by Andrés on 31/01/2016.
 */
import java.io.Serializable;

public class Discoteca implements Serializable{
    private int idDiscoteca;
    private String nombre="";
    private String emailUsr="";
    private String descripcion="";
    private String imagen;

    public int getIdDiscoteca() {
        return idDiscoteca;
    }

    public void setIdDiscoteca(int idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    public String getEmailUsr() {
        return emailUsr;
    }

    public void setEmailUsr(String emailUsr) {
        this.emailUsr = emailUsr;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Discoteca() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}

