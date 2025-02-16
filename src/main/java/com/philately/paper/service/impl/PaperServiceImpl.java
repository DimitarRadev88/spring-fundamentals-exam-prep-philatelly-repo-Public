package com.philately.paper.service.impl;

import com.philately.paper.dao.PaperRepository;
import com.philately.paper.model.Paper;
import com.philately.paper.model.PaperName;
import com.philately.paper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperRepository paperRepository;

    @Autowired
    public PaperServiceImpl(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    @Override
    public boolean isEmpty() {
        return paperRepository.count() <= 0;
    }

    @Override
    public void addAll(List<Paper> list) {
        paperRepository.saveAll(list);
    }

    @Override
    public Paper getPaper(PaperName name) {
        return paperRepository.findByName(name);
    }

}
