package com.threadwork.security;
import com.threadwork.model.Role;
import java.util.UUID;
/** The logged-in user. Controllers receive it with @AuthenticationPrincipal. */
public record AuthUser(UUID id, Role role, String name) {}
