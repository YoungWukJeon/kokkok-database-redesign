package me.kani.repository.renewal;

import me.kani.Repository;
import me.kani.entity.renewal.OperatingTimeEntity;
import me.kani.entity.renewal.types.OperatingTimeType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperatingTimeRepository implements Repository<OperatingTimeEntity, Long> {
    private final Connection connection;

    public OperatingTimeRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<OperatingTimeEntity> findAll() {
        final var sql = "SELECT * FROM operating_time";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<OperatingTimeEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var no = resultSet.getLong("no");
                final var spotNo = resultSet.getLong("spot_no");
                final var role = OperatingTimeType.valueOf(resultSet.getString("type"));
                final var time = resultSet.getString("time");

                result.add(new OperatingTimeEntity(no, spotNo, role, time));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
