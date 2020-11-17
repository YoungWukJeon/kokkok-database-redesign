package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.AccommodationEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccommodationRepository implements Repository<AccommodationEntity, String> {
    private final Connection connection;

    public AccommodationRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<AccommodationEntity> findAll() {
        final var sql = "SELECT * FROM accommodation";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<AccommodationEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var accNo = resultSet.getString("acc_no");
                final var name = resultSet.getString("name");
                final var address = resultSet.getString("address");
                final var latitude = resultSet.getDouble("latitude");
                final var longitude = resultSet.getDouble("longitude");
                final var phone = resultSet.getString("phone");
                final var intro = resultSet.getString("intro");
                final var subIntro = resultSet.getString("sub_intro");

                result.add(new AccommodationEntity(accNo, name, address,
                        latitude, longitude, phone, intro, subIntro));
            }

            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
