package com.example.prueba.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.prueba.model.NodeRoot;

public interface NodeRepository extends ReactiveMongoRepository<NodeRoot, ObjectId> {
	
}
