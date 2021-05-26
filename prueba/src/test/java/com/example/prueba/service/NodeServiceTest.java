package com.example.prueba.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.prueba.model.NodeDesc;
import com.example.prueba.model.NodeRoot;
import com.example.prueba.repository.NodeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NodeService.class})
class NodeServiceTest {

  @Autowired private NodeService service;
  @MockBean private NodeRepository repository;
  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
  }

  @Test
  void findAll() {
    List<NodeRoot> nodes =
        Arrays.asList(new NodeRoot("root"), new NodeDesc("desc", "node desc", null));
    when(repository.findAll()).thenReturn(Flux.fromIterable(nodes));

    var response = service.findAll().collectList().block();

    assertNotNull(response);
    assertEquals(2, response.size());
    assertEquals(NodeRoot.class, response.get(0).getClass());
    assertEquals(NodeDesc.class, response.get(1).getClass());
    assertEquals("root", response.get(0).getNombre());
    assertEquals("desc", response.get(1).getNombre());

    verify(repository).findAll();
  }

  @Test
  void insert() throws JsonProcessingException {
    var nodeRoot = new NodeRoot();
    nodeRoot.setNombre("root");

    var parentId = new ObjectId();
    var nodeDesc = new NodeDesc();
    nodeDesc.setNombre("desc");
    nodeDesc.setDescripcion("node desc");
    nodeDesc.setParentId(parentId);

    when(repository.insert(any(NodeRoot.class)))
        .then(
            invocation -> {
              NodeRoot n = invocation.getArgument(0);
              n.setId(new ObjectId());
              return Mono.just(n);
            });

    // insert NodeRoot test
    var nodeRootInsert = service.insert(nodeRoot).block();

    assertNotNull(nodeRootInsert);
    assertNotNull(nodeRootInsert.getId());
    assertEquals("root", nodeRootInsert.getNombre());

    // insert NodeDesc test
    var nodeDescInsert = service.insert(nodeDesc).block();
    var nodeDescInsertJson = mapper.readTree(mapper.writeValueAsString(nodeDescInsert));

    assertNotNull(nodeDescInsert);
    assertNotNull(nodeDescInsertJson.path("id"));
    assertEquals("desc", nodeDescInsertJson.path("nombre").asText());
    assertEquals("node desc", nodeDescInsertJson.path("descripcion").asText());
    assertEquals(parentId.toString(), nodeDescInsertJson.path("parentId").asText());

    verify(repository, times(2)).insert(any(NodeRoot.class));
  }
}
