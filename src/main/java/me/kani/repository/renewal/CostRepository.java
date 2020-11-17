package me.kani.repository.renewal;

import me.kani.Repository;
import me.kani.entity.renewal.CostEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CostRepository implements Repository<CostEntity, Long> {
    private final Connection connection;

    public CostRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<CostEntity> findAll() {
        final var sql = "SELECT * FROM cost";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<CostEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var no = resultSet.getLong("no");
                final var spotNo = resultSet.getLong("spot_no");
                final var content = resultSet.getString("content");
                final var price = resultSet.getInt("price");

                result.add(new CostEntity(no, spotNo, content, price));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
