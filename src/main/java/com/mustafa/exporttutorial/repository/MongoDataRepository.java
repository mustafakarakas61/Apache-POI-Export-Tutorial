package com.mustafa.exporttutorial.repository;

import com.mustafa.exporttutorial.model.MongoDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDataRepository extends MongoRepository<MongoDataModel, String> {
}
