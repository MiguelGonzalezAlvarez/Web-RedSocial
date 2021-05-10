package com.tew.model;

public class Amigos {
	private String email_usuario;
	private String email_amigo;
	private boolean aceptada;
	
	public String getEmail_usuario() {
		return email_usuario;
	}
	public void setEmail_usuario(String email_usuario) {
		this.email_usuario = email_usuario;
	}
	public String getEmail_amigo() {
		return email_amigo;
	}
	public void setEmail_amigo(String email_amigo) {
		this.email_amigo = email_amigo;
	}
	public boolean isAceptada() {
		return aceptada;
	}
	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}
	@Override
	public String toString() {
		
		return ("Usuario:"+email_usuario+"Amigo: "+email_amigo);
	}

	
	
}
