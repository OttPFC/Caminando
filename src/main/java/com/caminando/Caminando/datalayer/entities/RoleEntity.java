package com.caminando.Caminando.datalayer.entities;

import com.caminando.Caminando.datalayer.entities.travel.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class RoleEntity extends BaseEntity {

    private String roleType;
}
