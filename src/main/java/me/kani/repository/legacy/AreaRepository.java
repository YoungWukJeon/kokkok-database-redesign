package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.AreaEntity;
import me.kani.entity.legacy.types.AreaKind;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AreaRepository implements Repository<AreaEntity, String> {
    private final Connection connection;

    public AreaRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<AreaEntity> findAll() {
        final var sql = "SELECT * FROM area";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<AreaEntity> ();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var areaNo = resultSet.getString("area_no");
                final var name = resultSet.getString("name");
                final var address = resultSet.getString("address");
                final var latitude = resultSet.getDouble("latitude");
                final var longitude = resultSet.getDouble("longitude");
                final var phone = resultSet.getString("phone");
                final var intro = resultSet.getString("intro");
                final var time = resultSet.getString("time");
                final var cost = resultSet.getString("cost");
                final var kind = AreaKind.convert(resultSet.getString("kind"));

                result.add(new AreaEntity(areaNo, name, address,
                        latitude, longitude,
                        phone, intro, time, cost, kind));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}