package com.tew.model;


public class Usuario {
	
	private String email;
	private String passwd;
	private String rol;
	private String nombre;
	
		
	public Usuario(String emailRegistrado, String contraseñaRegistrado, String nombreRegistrado, String rolRegistrado) {
		email = emailRegistrado;
		passwd = contraseñaRegistrado;
		nombre = nombreRegistrado;
		rol = rolRegistrado;
	}
	public Usuario() 
	{
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
