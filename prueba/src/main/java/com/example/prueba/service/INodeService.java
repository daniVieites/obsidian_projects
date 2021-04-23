package com.example.prueba.service;

import com.example.prueba.model.NodeRoot;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface INodeService {
	Mono<Void> insert(Mono<NodeRoot> node);
	Flux<NodeRoot> findAll();
}
