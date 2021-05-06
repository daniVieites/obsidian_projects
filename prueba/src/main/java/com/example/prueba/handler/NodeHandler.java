package com.example.prueba.handler;

import java.net.URI;

import org.bson.BSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.prueba.model.NodeDesc;
import com.example.prueba.model.NodeRoot;
import com.example.prueba.service.INodeService;

import reactor.core.publisher.Mono;

@Component
public class NodeHandler {
	
	@Autowired
	private INodeService nodeService;

	public Mono<ServerResponse> findAll(ServerRequest serverRequest){
		return ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(nodeService.findAll(), NodeRoot.class);
	}
	
	public Mono<ServerResponse> insert(ServerRequest serverRequest){
		Mono<NodeDesc> node = serverRequest.bodyToMono(NodeDesc.class);
		return node
						.flatMap(n -> {
								NodeRoot root = new NodeRoot();
								if(n.getDescripcion() == null) {
									root.setNombre(n.getNombre());
								}else {
									root = n;
								}
								return nodeService
										.insert(root)
										.flatMap(ndb -> 
												ServerResponse
													.created(URI.create(""))
													.contentType(MediaType.APPLICATION_JSON)
													.body(BodyInserters.fromValue(ndb))
												);
						});
	}
}
