package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.UrlEntity;
import me.kani.entity.legacy.types.UrlKind;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UrlRepository implements Repository<UrlEntity, Void> {
    private final Connection connection;

    public UrlRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<UrlEntity> findAll() {
        final var sql = "SELECT * FROM url";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<UrlEntity>();
            return getUrlEntities(statement, result);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<UrlEntity> findAllByKind(UrlKind urlKind) {
        final var sql = "SELECT * FROM url WHERE kind = ?";

        try(final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<UrlEntity>();
            statement.setString(1, urlKind.revert());
            return getUrlEntities(statement, result);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<UrlEntity> getUrlEntities(PreparedStatement statement, List<UrlEntity> result) throws SQLException {
        final var resultSet = statement.executeQuery();

        while (resultSet.next()) {
            final var kind = UrlKind.convert(resultSet.getString("kind"));
            final var itemId = resultSet.getString("item_id");
            final var address = resultSet.getString("address");

            result.add(new UrlEntity(kind, itemId, address));
        }
        return result;
    }
}
