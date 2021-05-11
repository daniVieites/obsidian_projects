package com.example.prueba.model;

import org.bson.types.ObjectId;

public class NodeDesc extends NodeRoot{
	private String descripcion;
	private ObjectId parentId;
	
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

	public String getParentId() {
		return parentId.toString();
	}

	public void setParentId(String parentId) {
		this.parentId = new ObjectId(parentId);
	}

	@Override
	public String toString() {
		return "NodeDesc [descripcion=" + descripcion + "]";
	}
}
