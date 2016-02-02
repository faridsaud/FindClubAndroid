package ec.edu.epn.findclub.Application;

import android.app.Application;

import ec.edu.epn.findclub.VO.Usuario;

/**
 * Created by farid on 1/30/2016.
 */
public class MyApp extends Application {
    private Usuario usuarioLogeado;
    private String urlServicio="http://172.30.24.55:8080/FindClub/REST";

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    public String getUrlServicio() {
        return urlServicio;
    }

}
