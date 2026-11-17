package com.threadwork.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threadwork.model.Role;
import com.threadwork.model.User;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
public final class UserDtos {
  private UserDtos() {}
  public record AdminUserView(UUID id, String name, String email, Role role, String plan, @JsonProperty("created_at") Instant createdAt) {
    public static AdminUserView from(User u) { return new AdminUserView(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getPlan(), u.getCreatedAt()); }
  }
  public record RoleRequest(@NotNull Role role) {}
}
