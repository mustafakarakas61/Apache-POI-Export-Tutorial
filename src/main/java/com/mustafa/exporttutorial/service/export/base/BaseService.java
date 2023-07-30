package com.mustafa.exporttutorial.service.export.base;

import com.mustafa.exporttutorial.service.MongoDataService;

public abstract class BaseService {

    protected final String[] titles = {
            "R.Nu", "name", "surname", "age", "city", "birthday"
    };

    protected final MongoDataService mongoDataService;

    protected BaseService(MongoDataService mongoDataService) {
        this.mongoDataService = mongoDataService;
    }
}
