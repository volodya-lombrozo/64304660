package com.example0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private PageService pageService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        BaseModel baseModel = pageService.saveEntities();
        System.out.println(baseModel);
        List<BaseModel> baseModelsAfterSelect = pageService.getAllByThreeQueries();
        assert baseModelsAfterSelect != null;
		System.out.println(baseModelsAfterSelect);
    }


}


