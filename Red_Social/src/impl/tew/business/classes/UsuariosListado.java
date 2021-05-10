package impl.tew.business.classes;

import java.util.List;

import com.tew.infrastructure.Factories;
import com.tew.model.Usuario;
import com.tew.persistence.UsuarioDao;


public class UsuariosListado {

	public List<Usuario> getAlumnos() throws Exception 
	{
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return  dao.getUsuario();
	}

	public List<Usuario> getUsuariosFiltrados(String textoFiltro, String textoFiltro2) 
	{
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return  dao.getUsuarioFiltrado(textoFiltro, textoFiltro2);
	}

	public List<Usuario> getListadoPeticiones(String email) 
	{
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return  dao.getListadoPeticiones(email);
	}

	public List<Usuario> getListadoEnvios(String email) {
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return  dao.getListadoEnvios(email);
	}

	public List<Usuario> getInicioSesion(String login, String password) {
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return  dao.getInicioSesion(login, password);
	}

	public String getRol(String login) {
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return  dao.getRol(login);
	}
}
