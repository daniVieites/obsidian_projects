package com.example.prueba.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("node")
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({@JsonSubTypes.Type(value = NodeDesc.class, name = "NodeDesc")})
public class NodeRoot {
  @JsonSerialize(using = ToStringSerializer.class)
  @Id
  private ObjectId id;

  private String nombre = "";
  private List<NodeRoot> children = new ArrayList<>();

  public NodeRoot(String nombre) {
    this.nombre = nombre;
  }
}
