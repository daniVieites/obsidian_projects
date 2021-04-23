package com.example.prueba.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.prueba.model.NodeRoot;
import com.example.prueba.service.INodeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class NodeHandler {
	
	@Autowired
	private INodeService nodeService;

	public Mono<ServerResponse> findAll(ServerRequest serverRequest){
		Flux<NodeRoot> node = nodeService.findAll();
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(node, NodeRoot.class);
	}
	
	public Mono<ServerResponse> insert(ServerRequest serverRequest){
		Mono<NodeRoot> node = serverRequest.bodyToMono(NodeRoot.class);
		return ServerResponse.ok().build(nodeService.insert(node));
	}
}
