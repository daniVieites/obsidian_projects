package com.example.prueba.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class NodeDesc extends NodeRoot {
  private String descripcion;
  private ObjectId parentId;

  public NodeDesc(
      @JsonProperty("nombre") String nombre,
      @JsonProperty("descripcion") String descripcion,
      @JsonProperty("parentId") ObjectId parentId) {
    super(nombre);
    this.descripcion = descripcion;
    this.parentId = parentId;
  }

  public void setParentId(String id) {
    this.parentId = new ObjectId(id);
  }

  public String getParentid() {
    return parentId.toString();
  }

  @Override
  public int hashCode() {
    var prime = 31;
    int result = super.hashCode();
    result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    NodeDesc other = (NodeDesc) obj;
    if (descripcion == null) {
      if (other.descripcion != null) return false;
    } else if (!descripcion.equals(other.descripcion)) return false;
    return true;
  }
}
