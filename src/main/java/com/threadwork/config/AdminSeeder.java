package com.threadwork.config;
import com.threadwork.model.Role;
import com.threadwork.model.User;
import com.threadwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
/** Creates (or promotes) the first admin on startup when ADMIN_EMAIL and ADMIN_PASSWORD are set. */
@Component
public class AdminSeeder implements ApplicationRunner {
  private final UserRepository users; private final PasswordEncoder encoder;
  @Value("${app.admin-email:}") private String email;
  @Value("${app.admin-password:}") private String password;
  public AdminSeeder(UserRepository users, PasswordEncoder encoder) { this.users = users; this.encoder = encoder; }
  @Override public void run(ApplicationArguments args) {
    if (email.isBlank() || password.isBlank()) return;
    User u = users.findByEmail(email.trim().toLowerCase()).orElseGet(User::new);
    if (u.getId() == null) { u.setName("Owner"); u.setEmail(email.trim().toLowerCase()); u.setPasswordHash(encoder.encode(password)); }
    u.setRole(Role.admin); users.save(u);
  }
}
