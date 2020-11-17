package me.kani.entity.renewal;

import me.kani.entity.renewal.types.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public record MemberEntity(Long no, String thirdPartyId, String nickname,
                           Role role, String profileImage,
                           LocalDateTime createDate, LocalDateTime updateDate, LocalDateTime lastLoginDate) {
    public MemberEntity {
        Objects.requireNonNull(nickname);
        Objects.requireNonNull(role);
        Objects.requireNonNull(createDate);
        Objects.requireNonNull(updateDate);
        Objects.requireNonNull(lastLoginDate);
    }
}
