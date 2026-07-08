package com.skuhtin.spring_boot_demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 64)
  @Setter(AccessLevel.PUBLIC)
  private String username;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected Member() {
  }

  public Member(String username) {
    this.username = username;
    this.createdAt = Instant.now();
  }
}
