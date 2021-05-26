package com.example.prueba.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.prueba.model.NodeDesc;
import com.example.prueba.model.NodeRoot;
import com.example.prueba.service.NodeService;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest
class NodeHandlerTest {

  @Autowired private WebTestClient client;
  @MockBean private NodeService service;

  @Test
  void findAll() {
    List<NodeRoot> nodes =
        Arrays.asList(new NodeRoot("root"), new NodeDesc("desc", "node desc", null));
    when(service.findAll()).thenReturn(Flux.fromIterable(nodes));

    client
        .get()
        .uri("/nodes")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_NDJSON)
        .expectBodyList(NodeRoot.class)
        .consumeWith(
            response -> {
              List<NodeRoot> nodeList = response.getResponseBody();
              assertNotNull(nodeList);
              assertEquals(NodeRoot.class, nodeList.get(0).getClass());
              assertEquals(NodeDesc.class, nodeList.get(1).getClass());
              assertEquals("root", nodeList.get(0).getNombre());
              assertEquals("desc", nodeList.get(1).getNombre());
            })
        .hasSize(2);

    verify(service).findAll();
  }

  @Test
  void insert() {
    var nodeRoot = new NodeRoot();
    nodeRoot.setNombre("root");

    when(service.insert(any(NodeRoot.class)))
        .then(
            invocation -> {
              NodeRoot nr = invocation.getArgument(0);
              nr.setId(new ObjectId());
              return Mono.just(nr);
            });

    client
        .post()
        .uri("/insert")
        .contentType(MediaType.APPLICATION_NDJSON)
        .bodyValue(nodeRoot)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_NDJSON)
        .expectBody(NodeRoot.class)
        .consumeWith(
            response -> {
              NodeRoot n = response.getResponseBody();
              assertNotNull(n);
              assertNotNull(n.getId());
              assertEquals("root", n.getNombre());
            });

    verify(service).insert(any(NodeRoot.class));
  }

  @Test
  void insertNodeDesc() {
    var parentId = new ObjectId();
    var nodeDesc = new NodeDesc();
    nodeDesc.setNombre("desc");
    nodeDesc.setDescripcion("node desc");
    nodeDesc.setParentId(parentId);

    when(service.insert(any(NodeRoot.class)))
        .then(
            invocation -> {
              NodeRoot nr = invocation.getArgument(0);
              nr.setId(new ObjectId());
              return Mono.just(nr);
            });

    client
        .post()
        .uri("/insert")
        .contentType(MediaType.APPLICATION_NDJSON)
        .bodyValue(nodeDesc)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_NDJSON)
        .expectBody(NodeDesc.class)
        .consumeWith(
            response -> {
              NodeDesc n = response.getResponseBody();
              assertNotNull(n);
              assertNotNull(n.getId());
              assertEquals("desc", n.getNombre());
              assertEquals("node desc", n.getDescripcion());
              assertEquals(parentId.toString(), n.getParentId().toString());
            });

    verify(service).insert(any(NodeRoot.class));
  }
}
