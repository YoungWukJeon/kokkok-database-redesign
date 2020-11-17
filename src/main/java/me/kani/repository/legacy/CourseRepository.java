package me.kani.repository.legacy;

import me.kani.Repository;
import me.kani.entity.legacy.CourseEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseRepository implements Repository<CourseEntity, String> {
    private final Connection connection;

    public CourseRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<CourseEntity> findAll() {
        final var sql = "SELECT * FROM course";

        try (final var statement = connection.prepareStatement(sql)) {
            final var result = new ArrayList<CourseEntity>();
            final var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final var courseNo = resultSet.getString("course_no");
                final var name = resultSet.getString("name");
                final var route = resultSet.getString("route");
                final var isBasic = resultSet.getString("isBasic");
                final var nickname = resultSet.getString("nickname");

                result.add(new CourseEntity(courseNo, name, route, isBasic, nickname));
            }
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
