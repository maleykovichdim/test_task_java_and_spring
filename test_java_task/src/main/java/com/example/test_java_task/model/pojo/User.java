package com.example.test_java_task.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "agree_terms", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean agreeTerms;

    @OneToMany(
            mappedBy = "userId",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
          //,orphanRemoval = true
    )
    //@ToString.Exclude
    private List<UserSector> sectors;
}
