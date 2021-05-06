package com.example.prueba.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("node")
public class NodeRoot {
	@Id
	private ObjectId id;
	private String nombre;
	
	public NodeRoot() {}
	
	public NodeRoot(ObjectId id, String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id.toString();
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "NodeRoot [id=" + id + ", nombre=" + nombre + "]";
	}
}
