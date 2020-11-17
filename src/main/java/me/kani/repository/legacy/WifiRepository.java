package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.UrlEntity;
import me.kani.entity.legacy.WifiEntity;
import me.kani.entity.legacy.types.UrlKind;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WifiRepository implements Repository<WifiEntity, String> {
    private final Connection connection;

    public WifiRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<WifiEntity> findAll() {
        final var sql = "SELECT * FROM wifi";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<WifiEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var wifiNo = resultSet.getString("wifi_no");
                final var name = resultSet.getString("name");
                final var latitude = resultSet.getDouble("latitude");
                final var longitude = resultSet.getDouble("longitude");

                result.add(new WifiEntity(wifiNo, name, latitude, longitude));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
