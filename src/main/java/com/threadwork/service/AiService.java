package com.threadwork.service;
import com.threadwork.dto.AiDtos.AiResult;
import com.threadwork.exception.ApiException;
import com.threadwork.model.User;
import com.threadwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.UUID;
@Service @RequiredArgsConstructor
public class AiService {
  private static final Map<String, Integer> LIMITS = Map.of("free", 2, "creator", 30, "studio", Integer.MAX_VALUE);
  private final UserRepository users;
  @Value("${app.ai-enabled:false}") private boolean enabled;   // set AI_ENABLED=true once an image provider is connected
  /** Counts one AI picture against the user's plan. Over the limit answers 402 so the frontend can show the plans. */
  @Transactional public AiResult generate(UUID userId, String prompt) {
    if (!enabled) throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, "AI image generation is not connected yet. Upload your own picture for now.");
    User u = users.findById(userId).orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Log in first"));
    int limit = LIMITS.getOrDefault(u.getPlan(), 2);
    if (users.incrementAi(userId, limit) == 0) throw new ApiException(HttpStatus.PAYMENT_REQUIRED, "AI limit reached. Upgrade your plan.");
    // TODO: call your image provider with `prompt`, store the image, and return its URL as imageUrl
    return new AiResult(null, limit == Integer.MAX_VALUE ? null : limit - u.getAiUsed() - 1);
  }
}
