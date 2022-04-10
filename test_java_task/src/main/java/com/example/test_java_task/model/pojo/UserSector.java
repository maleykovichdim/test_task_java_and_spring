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
@Table(name = "users_sectors")
public class UserSector extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "sector_value")
    private Integer sectorValue;
}
