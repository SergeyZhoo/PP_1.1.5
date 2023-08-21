package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUsersTable() {
        try {
            String query = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255)," +
                    "age TINYINT)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            String query = "DROP TABLE IF EXISTS users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

            System.out.println("Таблица 'users' успешно удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            String query = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Пользователь с id " + id + " удален из базы данных");
            } else {
                System.out.println("Пользователь с id " + id + " не найден в базе данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
            String query = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);

                System.out.println("Инормация о пользователе: " + user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            String query = "DELETE FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Все пользователи удалены из базы данных");
            } else {
                System.out.println("Таблица пользователей уже пуста");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
