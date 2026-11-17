package com.threadwork.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threadwork.model.Message;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
public final class MessageDtos {
  private MessageDtos() {}
  public record MessageRequest(@NotBlank String name, @NotBlank @Email String email, @NotBlank String body) {}
  public record MessageView(UUID id, String name, String email, String body, @JsonProperty("created_at") Instant createdAt) {
    public static MessageView from(Message m) { return new MessageView(m.getId(), m.getName(), m.getEmail(), m.getBody(), m.getCreatedAt()); }
  }
}
