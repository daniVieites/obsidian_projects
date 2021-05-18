package com.example.prueba.handler;

import com.example.prueba.model.NodeRoot;
import com.example.prueba.service.INodeService;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NodeHandler {

  @Autowired private INodeService nodeService;

  public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_NDJSON)
        .body(nodeService.findAll(), NodeRoot.class);
  }

  public Mono<ServerResponse> insert(ServerRequest serverRequest) {
    var bodyString = serverRequest.bodyToMono(NodeRoot.class);
    return bodyString.flatMap(
        b ->
            nodeService
                .insert(b)
                .flatMap(
                    ndb ->
                        ServerResponse.created(URI.create(""))
                            .contentType(MediaType.APPLICATION_NDJSON)
                            .body(BodyInserters.fromValue(ndb))));
  }

  public Mono<ServerResponse> trees(ServerRequest serverRequest) {
    return nodeService
        .findRoots()
        .collectList()
        .flatMap(
            list -> {
              treeGenerator(list);
              return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON).bodyValue(list);
            })
        .delayElement(Duration.ofMillis(100));
  }

  public void treeGenerator(List<NodeRoot> list) {
    for (NodeRoot node : list) {
      Flux<NodeRoot> childs = nodeService.findChilds(node.getId());
      childs
          .collectList()
          .subscribe(
              nodeChilds -> {
                node.setChildren(nodeChilds);
                treeGenerator(nodeChilds);
              });
    }
  }
}
