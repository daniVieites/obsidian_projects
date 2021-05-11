package com.example.prueba.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.prueba.model.NodeRoot;

import reactor.core.publisher.Flux;

public interface NodeRepository extends ReactiveMongoRepository<NodeRoot, ObjectId> {
	Flux<NodeRoot> findByClass(Class className);
//	Flux<NodeRoot> findByDescripcionIsNull();
}
