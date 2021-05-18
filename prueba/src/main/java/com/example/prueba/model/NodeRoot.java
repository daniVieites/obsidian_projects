package com.example.prueba.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("node")
@Data
@JsonTypeInfo(
	  use = JsonTypeInfo.Id.NAME, 
	  include = JsonTypeInfo.As.PROPERTY, 
	  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = NodeDesc.class, name = "NodeDesc")
})
public class NodeRoot {
  @JsonSerialize(using = ToStringSerializer.class)
  @Id
  private ObjectId id;

  private String nombre;
  private List<NodeRoot> childs;

  public NodeRoot(String nombre) {
    this.nombre = nombre;
  }
}
