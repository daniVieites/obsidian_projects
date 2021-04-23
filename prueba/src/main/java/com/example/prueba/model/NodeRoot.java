package com.example.prueba.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("node")
public class NodeRoot {
	@Id
	private ObjectId id;
	private String nombre;
	
	public NodeRoot(ObjectId id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public ObjectId getId() {
		return id;
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
}
