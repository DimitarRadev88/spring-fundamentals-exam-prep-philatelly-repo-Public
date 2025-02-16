package com.philately.paper.service;

import com.philately.paper.model.Paper;
import com.philately.paper.model.PaperName;

import java.util.List;

public interface PaperService {
    boolean isEmpty();

    void addAll(List<Paper> list);

    Paper getPaper(PaperName source);
}
