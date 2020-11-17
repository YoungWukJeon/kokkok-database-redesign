package me.kani.repository.renewal;

import me.kani.Repository;
import me.kani.entity.renewal.SpotEntity;
import me.kani.entity.renewal.types.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpotRepository implements Repository<SpotEntity, Long> {
    private final Connection connection;

    public SpotRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SpotEntity> findAll() {
        final var sql = "SELECT * FROM `spot`";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<SpotEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var no = resultSet.getLong("no");
                final var areaName = resultSet.getString("area_name");
                final var category = Category.valueOf(resultSet.getString("category"));
                final var name = resultSet.getString("name");
                final var address = resultSet.getString("address");
                final var latitude = resultSet.getDouble("latitude");
                final var longitude = resultSet.getDouble("longitude");
                final var intro = resultSet.getString("intro");
                final var url = resultSet.getString("url");
                final var tags = resultSet.getString("tags");
                final var createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                final var creator = resultSet.getLong("creator");
                final var updateDate = resultSet.getTimestamp("update_date").toLocalDateTime();
                final var updater = resultSet.getLong("updater");

                result.add(new SpotEntity(no, areaName, category, name, address,
                        latitude, longitude, intro, url, tags,
                        createDate, creator, updateDate, updater));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void saveAll(List<SpotEntity> entities) {
        final var sql = """
            INSERT INTO `spot`
                (`area_name`, `category`, `name`, `address`, `latitude`, `longitude`, `intro`, `url`, `tags`, 
                `create_date`, `creator`, `update_date`, `updater`)
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try(final var statement = connection.prepareStatement(sql)) {
            entities.forEach(entity -> addBatchRow(statement, entity));
            final var resultRows = statement.executeBatch();
            System.out.println("InputRows: " + entities.size() + ", ResultRows: " + resultRows.length);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void addBatchRow(PreparedStatement statement, SpotEntity entity) {
        try {
            statement.setString(1, entity.areaName());
            statement.setString(2, entity.category().name());
            statement.setString(3, entity.name());
            statement.setString(4, entity.address());
            statement.setDouble(5, entity.latitude());
            statement.setDouble(6, entity.longitude());
            statement.setString(7, entity.intro());
            statement.setString(8, entity.url());
            statement.setString(9, entity.tags());
            statement.setTimestamp(10, Timestamp.valueOf(entity.createDate()));
            statement.setLong(11, entity.creator());
            statement.setTimestamp(12, Timestamp.valueOf(entity.updateDate()));
            statement.setLong(13, entity.updater());
            statement.addBatch();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}