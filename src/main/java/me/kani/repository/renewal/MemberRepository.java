package me.kani.repository.renewal;

import me.kani.Repository;
import me.kani.entity.renewal.MemberEntity;
import me.kani.entity.renewal.types.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberRepository implements Repository<MemberEntity, Long> {
    private final Connection connection;

    public MemberRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<MemberEntity> findAll() {
        final var sql = "SELECT * FROM `member`";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<MemberEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var no = resultSet.getLong("no");
                final var thirdPartyId = resultSet.getString("third_party_id");
                final var nickname = resultSet.getString("nickname");
                final var role = Role.valueOf(resultSet.getString("role"));
                final var profileImage = resultSet.getString("profile_image");
                final var createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                final var updateDate = resultSet.getTimestamp("update_date").toLocalDateTime();
                final var lastLoginDate = resultSet.getTimestamp("last_login_date").toLocalDateTime();

                result.add(new MemberEntity(no, thirdPartyId, nickname, role, profileImage, createDate, updateDate, lastLoginDate));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void save(MemberEntity entity) {
        final var sql = """
            INSERT INTO `member`
                (`nickname`, `role`, `create_date`, `update_date`, `last_login_date`)
                VALUES 
                (?, ?, ?, ?, ?)
        """;

        try(final var statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.nickname());
            statement.setString(2, entity.role().name());
            statement.setTimestamp(3, Timestamp.valueOf(entity.createDate()));
            statement.setTimestamp(4, Timestamp.valueOf(entity.updateDate()));
            statement.setTimestamp(5, Timestamp.valueOf(entity.lastLoginDate()));
            final var resultRows = statement.executeUpdate();

            System.out.println("ResultRows: " + resultRows);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
