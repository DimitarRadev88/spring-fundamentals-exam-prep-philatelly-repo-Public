package com.philately.stamp.model;

import com.philately.paper.model.Paper;
import com.philately.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "stamps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Paper paper;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User owner;

}
