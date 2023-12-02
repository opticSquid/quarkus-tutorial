package com.hangout.experiment;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Cacheable
public class Users extends PanacheEntity {

  @Column(length = 40)
  public String name;

}
