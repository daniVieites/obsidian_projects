package com.example.prueba.service;

import com.example.prueba.model.NodeRoot;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface INodeService {
  Mono<NodeRoot> insert(NodeRoot node);

  Flux<NodeRoot> findAll();

  Flux<NodeRoot> findRoots();

  Flux<NodeRoot> findChilds(String objectId);
}
