package com.skuhtin.spring_boot_demo;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.skuhtin.spring_boot_demo.entity.Member;
import com.skuhtin.spring_boot_demo.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberRepositoryIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private MemberRepository repo;

  @Test
  void findByExistName() {
    Optional<Member> member = repo.findByUsername("john_doe");
    assertThat(member.isPresent()).isTrue();
  }

  @Test
  void findByAbsentName() {
    boolean isPresent = repo.existsByUsername("Unknown");
    assertThat(isPresent).isFalse();
  }

}
