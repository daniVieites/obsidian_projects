package com.example.prueba.handler;

import com.example.prueba.model.NodeRoot;
import com.example.prueba.service.INodeService;
import java.net.URI;
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
    var flux = nodeService.findRoots().flatMap(this::treeGenerator);

    return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON).bodyValue(flux);
  }

  public Mono<NodeRoot> treeGenerator(NodeRoot node) {
    nodeService
        .findChildren(node.getId())
        .collectList()
        .subscribe(
            nodeChildren -> {
              node.setChildren(nodeChildren);
              for (NodeRoot nodeDesc : nodeChildren) {
                treeGenerator(nodeDesc);
              }
            });

    return Mono.just(node);
  }
}
