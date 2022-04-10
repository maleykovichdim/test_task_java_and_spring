package com.example.test_java_task.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sectors")
public class Sector extends BaseEntity {

    @Column(name = "value")
    private Integer value;

    @Column(name = "name")
    private String name;

    @Column(name = "related_id")
    private Integer relatedId;

    @Column(name = "depth")
    private Integer depth;

}
