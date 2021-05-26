package com.example.prueba.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.prueba.model.NodeDesc;
import com.example.prueba.model.NodeRoot;
import com.example.prueba.service.NodeService;
import java.util.Arrays;
import java.util.Collections;
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

  @Test
  void trees() {
    var id1 = new ObjectId();
    var id2 = new ObjectId();
    var id1_1 = new ObjectId();

    var root1 = new NodeRoot("root1");
    root1.setId(id1);
    var root2 = new NodeRoot("root2");
    root2.setId(id2);

    var desc1_1 = new NodeDesc("desc1_1", null, id1);
    desc1_1.setId(id1_1);
    var desc1_2 = new NodeDesc("desc1_2", null, id1);
    var desc1_1_1 = new NodeDesc("desc1_1_1", null, id1_1);
    var desc1_1_2 = new NodeDesc("desc1_1_2", null, id1_1);
    var desc2_1 = new NodeDesc("desc2_1", null, id2);

    var roots = Arrays.asList(root1, root2);
    var root1Children = Arrays.asList(desc1_1, desc1_2);
    var root2Children = Collections.singletonList(desc2_1);
    var desc1_1Children = Arrays.asList(desc1_1_1, desc1_1_2);

    when(service.findRoots()).thenReturn(Flux.fromIterable(roots));
    when(service.findChildren(id1)).thenReturn(Flux.fromIterable(root1Children));
    when(service.findChildren(id2)).thenReturn(Flux.fromIterable(root2Children));
    when(service.findChildren(id1_1)).thenReturn(Flux.fromIterable(desc1_1Children));
    when(service.findChildren(isNull())).thenReturn(Flux.fromIterable(Collections.emptyList()));

    client
        .get()
        .uri("/trees")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_NDJSON)
        .expectBodyList(NodeRoot.class)
        .consumeWith(
            response -> {
              var list = response.getResponseBody();
              assertNotNull(list);
              assertEquals("root1", list.get(0).getNombre());

              assertEquals(2, list.get(0).getChildren().size());
              assertEquals("desc1_1", list.get(0).getChildren().get(0).getNombre());
              assertEquals("desc1_2", list.get(0).getChildren().get(1).getNombre());

              assertEquals(2, list.get(0).getChildren().get(0).getChildren().size());
              assertEquals(
                  "desc1_1_1", list.get(0).getChildren().get(0).getChildren().get(0).getNombre());
              assertEquals(
                  "desc1_1_2", list.get(0).getChildren().get(0).getChildren().get(1).getNombre());

              assertEquals("root2", list.get(1).getNombre());

              assertEquals(2, list.get(1).getChildren().size());
              assertEquals("desc2_1", list.get(1).getChildren().get(0).getNombre());
            })
        .hasSize(2);
  }
}
