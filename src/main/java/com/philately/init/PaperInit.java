package com.philately.init;

import com.philately.paper.model.Paper;
import com.philately.paper.model.PaperName;
import com.philately.paper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PaperInit implements CommandLineRunner {

    private final PaperService paperService;

    @Autowired
    public PaperInit(PaperService paperService) {
        this.paperService = paperService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (paperService.isEmpty()) {
            List<Paper> list = Arrays.stream(PaperName.values())
                    .map(paperName -> {
                        Paper paper = new Paper();
                        paper.setName(paperName);
                        paper.setDescription(paperName.getDescription());
                        return paper;
                    }).toList();

            paperService.addAll(list);
        }
    }
}
