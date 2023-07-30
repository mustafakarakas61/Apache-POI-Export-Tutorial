package com.mustafa.exporttutorial.service;

import com.mustafa.exporttutorial.model.MongoDataModel;
import com.mustafa.exporttutorial.repository.MongoDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoDataService {

    private final MongoDataRepository mongoDataRepository;

    public MongoDataService(MongoDataRepository mongoDataRepository) {
        this.mongoDataRepository = mongoDataRepository;
    }

    public MongoDataModel createData(MongoDataModel mongoDataModel) {
        return mongoDataRepository.save(mongoDataModel);
    }

    public List<MongoDataModel> readAllData() {
        return mongoDataRepository.findAll();
    }

    public void deleteAllData() {
        mongoDataRepository.deleteAll();
    }
}
