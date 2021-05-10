package impl.tew.persistence;


import java.sql.*;
import java.util.*;
import com.tew.persistence.AdministradorDao;
import com.tew.persistence.exception.*;
/**
 * 
 * @author Jácome y Miguel
 *
 */
public class AdministradorJdbcDao implements AdministradorDao {	
	
	
	
	
	@Override
	public void borrarUsarios(List<String> a) throws NotPersistedException {
		
		
		
		
		if(!a.isEmpty()) {
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			Connection con = null;
			int rows = 0;
			String listaParaBorrar = "(";
			for(String s : a) {
				listaParaBorrar+= ("'"+s+"',");
			}
			listaParaBorrar = listaParaBorrar.substring(0, listaParaBorrar.length() - 1) + ")";
			
			try {
				
				String SQL_DRV = "org.hsqldb.jdbcDriver";
				String SQL_URL = "jdbc:hsqldb:hsql://localhost/localDB";
	
				
				
				Class.forName(SQL_DRV);
				con = DriverManager.getConnection(SQL_URL, "sa", "");
				ps1 = con.prepareStatement("DELETE FROM Amigos where email_usuario in "+listaParaBorrar+" OR email_amigo in "+listaParaBorrar);
				ps2 = con.prepareStatement("DELETE FROM Publicacion where email in "+listaParaBorrar);
				ps3 = con.prepareStatement("DELETE FROM Usuarios where email in "+listaParaBorrar);
				
				ps1.executeUpdate();
				ps2.executeUpdate();
				rows = ps3.executeUpdate();
				
				if (rows == 0) {
					throw new NotPersistedException("Users not found");
				} 
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new PersistenceException("Driver not found", e);
			} catch (SQLSyntaxErrorException  e) {
				e.printStackTrace();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new PersistenceException("Invalid SQL or database schema", e);
			}
			
			finally  {
				if (ps1 != null || ps2 != null || ps3 != null) {try{ ps1.close();ps2.close();ps3.close(); } catch (Exception ex){}};
				if (con != null) {try{ con.close(); } catch (Exception ex){}};
			}
		}
		else {System.out.println("No se puede borrar ese usuario o No hay usuarios selecionados");}
	}

	@SuppressWarnings("resource")
	@Override
	public void reiniciarBD() {
		PreparedStatement ps = null;
		
		Connection con = null;

		try {
			 
			String SQL_DRV = "org.hsqldb.jdbcDriver";
			String SQL_URL = "jdbc:hsqldb:hsql://localhost/localDB";
			
			Class.forName(SQL_DRV);
			con = DriverManager.getConnection(SQL_URL, "sa", "");
			

			try {

				for(String s:DB_schema()) {
					ps=con.prepareStatement(s);
					ps.execute();
				}
				for(String s:DB_data()) {
					ps=con.prepareStatement(s);
					ps.execute();
				}

	        } catch (Exception e) {
	        	System.out.println(e);
	        }

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenceException("Driver not found", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			if (ps != null) {try{ ps.close(); } catch (Exception ex){}};
			if (con != null) {try{ con.close(); } catch (Exception ex){}};
		}
		
	}

	private List<String> DB_schema(){
		List<String> l = new ArrayList<String>();
		l.add("DROP TABLE Amigos;");
		l.add("DROP TABLE Publicacion;");
		l.add("DROP TABLE Usuarios;");
		l.add("CREATE TABLE Usuarios (email varchar(100) PRIMARY KEY, passwd varchar(100) NOT NULL,rol varchar(20) not null,nombre varchar(100) not null);");
		l.add("CREATE TABLE Publicacion (ID int PRIMARY KEY, email varchar(100) NOT NULL REFERENCES Usuarios (email),titulo varchar(20) not null,texto varchar(300) not null,fecha bigint not null);");
		l.add("CREATE TABLE Amigos (email_usuario varchar(100) REFERENCES Usuarios (email), email_amigo varchar(100) REFERENCES Usuarios (email), aceptada boolean not null);");
		return l;
	}
	
	private List<String> DB_data(){
		List<String> l = new ArrayList<String>();
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('admin@email.es', 'admin', 'admin', 'Administrador');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user1@email.es', 'user1', 'usuario', 'Usuario_1');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user2@email.es', 'user2', 'usuario', 'Usuario_2');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user3@email.es', 'user3', 'usuario', 'Usuario_3');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user4@email.es', 'user4', 'usuario', 'Usuario_4');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user5@email.es', 'user5', 'usuario', 'Usuario_5');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user6@email.es', 'user6', 'usuario', 'Usuario_6');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user7@email.es', 'user7', 'usuario', 'Usuario_7');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user8@email.es', 'user8', 'usuario', 'Usuario_8');");
		l.add("INSERT INTO Usuarios (email, passwd, rol, nombre) VALUES ('user9@email.es', 'user9', 'usuario', 'Usuario_9');");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('1','user1@email.es', 'Titulo1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1612774130000);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('2','user1@email.es', 'Titulo2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1664824530654);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('3','user1@email.es', 'Titulo3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1546324530754);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('4','user2@email.es', 'Titulo4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1604274136450);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('5','user2@email.es', 'Titulo5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1564824536570);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('6','user2@email.es', 'Titulo6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1306453306436);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('7','user3@email.es', 'Titulo7', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1565824530543);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('8','user3@email.es', 'Titulo8', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1504274133450);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('9','user3@email.es', 'Titulo8', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1530645335740);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('10','user4@email.es', 'Titulo10', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1530645334560);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('11','user4@email.es', 'Titulo11', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1541150934360);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('12','user4@email.es', 'Titulo12', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1564824530126);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('13','user5@email.es', 'Titulo13', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1564274130436);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('14','user5@email.es', 'Titulo14', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1541150930643);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('15','user5@email.es', 'Titulo15', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1565454530476);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('16','user6@email.es', 'Titulo16', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1504274130457);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('17','user6@email.es', 'Titulo17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1530645330654);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('18','user6@email.es', 'Titulo18', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1541150976530);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('19','user7@email.es', 'Titulo19', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1564827654530);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('20','user7@email.es', 'Titulo20', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1541156783360);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('21','user7@email.es', 'Titulo21', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1504274138760);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('22','user8@email.es', 'Titulo22', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1541150789930);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('23','user8@email.es', 'Titulo23', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1570648765330);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('24','user8@email.es', 'Titulo24', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1504278794130);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('25','user9@email.es', 'Titulo25', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1541150870930);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('26','user9@email.es', 'Titulo26', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1530665330980);");
		l.add("INSERT INTO Publicacion (ID,email, titulo, texto, fecha) VALUES ('27','user9@email.es', 'Titulo27', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus est ante, consequat nec risus quam.', 1573645339860);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user1@email.es','user2@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user1@email.es','user3@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user1@email.es','user4@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user2@email.es','user1@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user2@email.es','user3@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user2@email.es','user4@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user3@email.es','user4@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user4@email.es','user3@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user5@email.es','user6@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user5@email.es','user7@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user5@email.es','user8@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user6@email.es','user5@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user6@email.es','user7@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user6@email.es','user8@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user7@email.es','user8@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user8@email.es','user7@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user9@email.es','user1@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user9@email.es','user2@email.es',1);");
		l.add("INSERT INTO Amigos (email_usuario,email_amigo,aceptada) VALUES ('user9@email.es','user3@email.es',1);");

		
		return l;
	}

}
