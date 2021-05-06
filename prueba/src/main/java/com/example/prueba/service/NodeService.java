package com.example.prueba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.model.NodeRoot;
import com.example.prueba.repository.NodeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NodeService implements INodeService{
	
	@Autowired
	private NodeRepository nodeRepo;

	@Override
	public Mono<NodeRoot> insert(NodeRoot node) {
		return nodeRepo.insert(node);
	}

	@Override
	public Flux<NodeRoot> findAll() {
		return nodeRepo.findAll();
	}
}
