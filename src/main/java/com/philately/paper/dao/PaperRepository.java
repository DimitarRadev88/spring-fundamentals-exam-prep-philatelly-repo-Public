package com.philately.paper.dao;

import com.philately.paper.model.Paper;
import com.philately.paper.model.PaperName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaperRepository extends JpaRepository<Paper, UUID> {
    Paper findByName(PaperName name);
}
