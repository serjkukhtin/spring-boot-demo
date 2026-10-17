package com.skuhtin.spring_boot_demo.controller;

import com.skuhtin.spring_boot_demo.dto.MemberDto;
import com.skuhtin.spring_boot_demo.dto.MemberRequest;
import com.skuhtin.spring_boot_demo.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService service;

  @GetMapping
  public List<MemberDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public MemberDto getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @GetMapping("/by-username/{username}")
  public MemberDto getByUsername(@PathVariable String username) {
    return service.getByUsername(username);
  }

  @GetMapping("/search")
  public List<MemberDto> search(@RequestParam String prefix) {
    return service.searchByUsernamePrefix(prefix);
  }

  @GetMapping("/count")
  public long count() {
    return service.count();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MemberDto create(@RequestBody MemberRequest request) {
    return service.create(request.username());
  }

  @PutMapping("/{id}")
  public MemberDto rename(@PathVariable Long id, @RequestBody MemberRequest request) {
    return service.rename(id, request.username());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
