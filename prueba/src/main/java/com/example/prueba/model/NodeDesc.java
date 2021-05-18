package com.example.prueba.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NodeDesc extends NodeRoot {
  private String descripcion = "";

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId parentId;

  public NodeDesc(String nombre, String descripcion, ObjectId parentId) {
    super(nombre);
    this.descripcion = descripcion;
    this.parentId = parentId;
  }
}
