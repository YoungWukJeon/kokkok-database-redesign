package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.ShoppingEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingRepository implements Repository<ShoppingEntity, String> {
    private final Connection connection;

    public ShoppingRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ShoppingEntity> findAll() {
        final var sql = "SELECT * FROM shopping";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<ShoppingEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var shopNo = resultSet.getString("shop_no");
                final var name = resultSet.getString("name");
                final var phone = resultSet.getString("phone");
                final var address = resultSet.getString("address");
                final var latitude = resultSet.getDouble("latitude");
                final var longitude = resultSet.getDouble("longitude");
                final var intro = resultSet.getString("intro");
                final var time = resultSet.getString("time");

                result.add(new ShoppingEntity(shopNo, name, phone, address,
                        latitude, longitude, intro, time));
            }

            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
