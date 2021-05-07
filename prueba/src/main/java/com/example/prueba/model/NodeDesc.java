package com.example.prueba.model;

import org.bson.types.ObjectId;

public class NodeDesc extends NodeRoot{
	private String descripcion;
	
	public NodeDesc() {}
	
	public NodeDesc(String nombre, String descripcion) {
		super(nombre);
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "NodeDesc [descripcion=" + descripcion + "]";
	}
}
