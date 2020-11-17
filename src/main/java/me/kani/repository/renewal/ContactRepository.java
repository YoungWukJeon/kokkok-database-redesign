package me.kani.repository.renewal;

import me.kani.Repository;
import me.kani.entity.renewal.ContactEntity;
import me.kani.entity.renewal.types.ContactType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactRepository implements Repository<ContactEntity, Long> {
    private final Connection connection;

    public ContactRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ContactEntity> findAll() {
        final var sql = "SELECT * FROM contact";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<ContactEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var no = resultSet.getLong("no");
                final var spotNo = resultSet.getLong("spot_no");
                final var type = ContactType.valueOf(resultSet.getString("type"));
                final var phone = resultSet.getString("phone");

                result.add(new ContactEntity(no, spotNo, type, phone));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
