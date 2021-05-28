package com.example.prueba.repository;

import com.example.prueba.model.NodeRoot;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface NodeRepository extends ReactiveMongoRepository<NodeRoot, ObjectId> {
  @Query("{'parentId': {$exists: false}}")
  Flux<NodeRoot> findByParentIdNot();

  @Query("{'parentId': ?0}")
  Flux<NodeRoot> findByParentId(ObjectId id);
}
