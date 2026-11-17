package com.threadwork.service;
import com.threadwork.dto.UserDtos.AdminUserView;
import com.threadwork.exception.ApiException;
import com.threadwork.model.Role;
import com.threadwork.model.User;
import com.threadwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service @RequiredArgsConstructor
public class UserService {
  private final UserRepository users;
  @Transactional(readOnly = true) public List<AdminUserView> list() { return users.findAllByOrderByCreatedAtDesc().stream().map(AdminUserView::from).toList(); }
  @Transactional public AdminUserView changeRole(UUID actorId, UUID id, Role role) {
    if (actorId.equals(id)) throw new ApiException(HttpStatus.BAD_REQUEST, "You cannot change your own role");
    User u = users.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
    u.setRole(role);
    return AdminUserView.from(u);
  }
}
