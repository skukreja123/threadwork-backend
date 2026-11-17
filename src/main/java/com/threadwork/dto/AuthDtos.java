package com.threadwork.dto;
import com.threadwork.model.Role;
import com.threadwork.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
public final class AuthDtos {
  private AuthDtos() {}
  public record SignupRequest(@NotBlank String name, @NotBlank @Email String email, @NotBlank @Size(min = 8, message = "must be at least 8 characters") String password) {}
  public record LoginRequest(@NotBlank String email, @NotBlank String password) {}
  public record UserView(UUID id, String name, String email, Role role, String plan) {
    public static UserView from(User u) { return new UserView(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getPlan()); }
  }
  public record AuthResponse(String token, UserView user) {}
}
