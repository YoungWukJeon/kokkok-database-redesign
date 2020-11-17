package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.RestaurantEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantRepository implements Repository<RestaurantEntity, String> {
    private final Connection connection;

    public RestaurantRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<RestaurantEntity> findAll() {
        final var sql = "SELECT * FROM restaurant";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<RestaurantEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var rstNo = resultSet.getString("rst_no");
                final var name = resultSet.getString("name");
                final var address = resultSet.getString("address");
                final var latitude = resultSet.getDouble("latitude");
                final var longitude = resultSet.getDouble("longitude");
                final var phone = resultSet.getString("phone");
                final var intro = resultSet.getString("intro");
                final var time = resultSet.getString("time");
                final var dayOff = resultSet.getString("day_off");
                final var menu = resultSet.getString("menu");

                result.add(new RestaurantEntity(rstNo, name, address,
                        latitude, longitude, phone, intro, time, dayOff, menu));
            }

            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
