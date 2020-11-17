package me.kani.repository.renewal;

import me.kani.Connector;
import me.kani.entity.renewal.MemberEntity;
import me.kani.entity.renewal.types.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MemberRepositoryTest {
    private final Connection renewalConnection = new Connector("localhost", 3306, "kokkok",
            "root", "rootpass").getConnection();

    @BeforeEach
    public void beginTransaction() throws SQLException {
        renewalConnection.setAutoCommit(false);
    }

    @AfterEach
    public void endTransaction() throws SQLException {
        renewalConnection.commit();
    }

    @Test
    @DisplayName("유저 데이터 가져오기")
    public void findAllTest() {
        final var memberRepository = new MemberRepository(renewalConnection);

        memberRepository.findAll()
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("유저 데이터 입력")
    public void insertTest() {
        final var memberRepository = new MemberRepository(renewalConnection);
        final var now = LocalDateTime.now();
        final var entity = new MemberEntity(null, null, "ADMIN_KANI", Role.ADMIN, null, now, now, now);

        memberRepository.save(entity);
    }
}