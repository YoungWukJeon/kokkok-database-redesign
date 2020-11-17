package me.kani.repository.renewal;

import me.kani.Repository;
import me.kani.entity.renewal.ConvenienceEntity;
import me.kani.entity.renewal.types.ContactType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvenienceRepository implements Repository<ConvenienceEntity, Long> {
    private final Connection connection;

    public ConvenienceRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ConvenienceEntity> findAll() {
        final var sql = "SELECT * FROM convenience";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<ConvenienceEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var no = resultSet.getLong("no");
                final var spotNo = resultSet.getLong("spot_no");
                final var content = resultSet.getString("content");

                result.add(new ConvenienceEntity(no, spotNo, content));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
