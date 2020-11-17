package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.ImageEntity;
import me.kani.entity.legacy.types.ImageKind;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageRepository implements Repository<ImageEntity, Void> {
    private final Connection connection;

    public ImageRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ImageEntity> findAll() {
        final var sql = "SELECT * FROM image";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<ImageEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var kind = ImageKind.convert(resultSet.getString("kind"));
                final var itemId = resultSet.getString("item_id");
                final var url = resultSet.getString("url");

                result.add(new ImageEntity(kind, itemId, url));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
