package com.skuhtin.spring_boot_demo.service;

import com.skuhtin.spring_boot_demo.dto.MemberDto;
import com.skuhtin.spring_boot_demo.entity.Member;
import com.skuhtin.spring_boot_demo.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository repository;

  public List<MemberDto> getAll() {
    return repository.findAll().stream().map(MemberDto::from).toList();
  }

  public MemberDto getById(Long id) {
    return MemberDto.from(findOrThrow(id));
  }

  public MemberDto getByUsername(String username) {
    return repository.findByUsername(username)
        .map(MemberDto::from)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
  }

  public List<MemberDto> searchByUsernamePrefix(String prefix) {
    return repository.findAll().stream()
        .filter(m -> m.getUsername().toLowerCase().startsWith(prefix.toLowerCase()))
        .map(MemberDto::from)
        .toList();
  }

  public long count() {
    return repository.findAll().size();
  }

  @Transactional
  public MemberDto create(String username) {
    if (repository.existsByUsername(username)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
    }
    return MemberDto.from(repository.save(new Member(username)));
  }

  @Transactional
  public MemberDto rename(Long id, String newUsername) {
    Member member = findOrThrow(id);
    if (repository.findByUsername(newUsername).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
    }
    member.setUsername(newUsername);
    return MemberDto.from(repository.save(member));
  }

  @Transactional
  public void delete(Long id) {
    findOrThrow(id);
    repository.deleteById(id);
  }

  private Member findOrThrow(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
  }
}
