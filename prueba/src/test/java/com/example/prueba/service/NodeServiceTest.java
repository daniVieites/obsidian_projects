package com.example.prueba.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.prueba.model.NodeDesc;
import com.example.prueba.model.NodeRoot;
import com.example.prueba.repository.NodeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
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
  void insertNodeRoot() throws JsonProcessingException {
    var nodeRoot = new NodeRoot();
    nodeRoot.setNombre("root");

    when(repository.insert(any(NodeRoot.class)))
        .then(
            invocation -> {
              NodeRoot n = invocation.getArgument(0);
              n.setId(new ObjectId());
              return Mono.just(n);
            });

    var nodeRootInsert = service.insert(nodeRoot).block();

    assertNotNull(nodeRootInsert);
    assertNotNull(nodeRootInsert.getId());
    assertEquals("root", nodeRootInsert.getNombre());

    verify(repository).insert(any(NodeRoot.class));
  }

  @Test
  void insertNodeDesc() throws JsonProcessingException {
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

    var nodeDescInsert = (NodeDesc) service.insert(nodeDesc).block();

    assertNotNull(nodeDescInsert);
    assertNotNull(nodeDescInsert.getId());
    assertEquals("desc", nodeDescInsert.getNombre());
    assertEquals("node desc", nodeDescInsert.getDescripcion());
    assertEquals(parentId.toString(), nodeDescInsert.getParentId().toString());

    verify(repository).insert(any(NodeRoot.class));
  }

  @Test
  void findRoots() {
    List<NodeRoot> roots = Arrays.asList(new NodeRoot("root1"), new NodeRoot("root2"));
    when(repository.findByClassName(anyString())).thenReturn(Flux.fromIterable(roots));

    var rootList = service.findRoots().collectList().block();

    assertNotNull(rootList);
    assertEquals(2, rootList.size());
    assertEquals("root1", rootList.get(0).getNombre());
    assertEquals("root2", rootList.get(1).getNombre());

    verify(repository).findByClassName(anyString());
  }

  @Test
  void findChildren() {
    var parentId = new ObjectId();
    var nodeDesc1 = new NodeDesc("desc1", "node desc 1", parentId);
    var nodeDesc2 = new NodeDesc("desc2", "node desc 2", parentId);

    List<NodeRoot> children = Arrays.asList(nodeDesc1, nodeDesc2);

    when(repository.findByParentId(parentId)).thenReturn(Flux.fromIterable(children));

    var nodeChildren = service.findChildren(parentId).collectList().block();

    assertNotNull(nodeChildren);
    assertEquals(2, nodeChildren.size());
    assertEquals("desc1", nodeChildren.get(0).getNombre());
    assertEquals("desc2", nodeChildren.get(1).getNombre());
    assertEquals(parentId.toString(), ((NodeDesc) nodeChildren.get(0)).getParentId().toString());
    assertEquals(parentId.toString(), ((NodeDesc) nodeChildren.get(1)).getParentId().toString());

    verify(repository).findByParentId(parentId);
  }
}
