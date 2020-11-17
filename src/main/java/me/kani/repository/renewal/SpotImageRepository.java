package me.kani.repository.renewal;

import me.kani.Repository;
import me.kani.entity.renewal.SpotImageEntity;
import me.kani.entity.renewal.types.State;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpotImageRepository implements Repository<SpotImageEntity, Long> {
    private final Connection connection;

    public SpotImageRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SpotImageEntity> findAll() {
        final var sql = "SELECT * FROM spot_image";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<SpotImageEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var no = resultSet.getLong("no");
                final var spotNo = resultSet.getLong("spot_no");
                final var url = resultSet.getString("url");
                final var representative = resultSet.getBoolean("representative");
                final var state = State.valueOf(resultSet.getString("state"));
                final var createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                final var creator = resultSet.getLong("creator");

                result.add(new SpotImageEntity(no, spotNo, url, representative, state, createDate, creator));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
