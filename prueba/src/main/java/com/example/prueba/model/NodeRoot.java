package com.example.prueba.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("node")
@Data
@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "className")
public class NodeRoot {
  @Id private ObjectId id;
  private String nombre;
  private List<NodeRoot> childs;

  public NodeRoot(@JsonProperty("nombre") String nombre) {
    this.nombre = nombre;
  }

  public String getId() {
    return id.toString();
  }
}
