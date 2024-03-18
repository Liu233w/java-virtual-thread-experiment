package com.shuminliu.experiment.io_test.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dummy", schema = "public")
public class DummyEntity {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;
}
