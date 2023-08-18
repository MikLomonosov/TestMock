package com.example.TestMockv2.DAL;

import com.example.TestMockv2.Models.User;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDate;

public class DataBaseWorker {

    //region Fields

    private String dbUrl = "jdbc:postgresql://0.0.0.0:5432/postgres";
    private String dbUser = "postgres";
    private String dbPassword = "postgres";
    private String authenticateTable = "authenticate";
    private String mailTable = "mail";

    //endregion

    //region Constructors

    public DataBaseWorker() {}

    //endregion

    //region Methods

    public User GetUserByLogin(String login) {

        String sqlString = MessageFormat.format(
                "SELECT {0}.login, {0}.password, " +
                        "{0}.date, {1}.email " +
                        "FROM {0} " +
                        "JOIN {1} " +
                        "ON ({0}.login = {1}.login) " +
                        "WHERE {0}.login = ''"+ login + "''", authenticateTable, mailTable);


        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlString);

            if(resultSet.next()) {
                User user = new User();

                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setDate(resultSet.getDate("date"));

                return user;
            }
            // I know null return is bad :D
            return null;

        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String InsertUser(User user) {

        String sqlQuery = MessageFormat.format(
                "WITH insert_query as ( " +
                        "INSERT INTO {0} (login, password, date) " +
                        "VALUES (?, ?, ?) " +
                        "returning login " +
                        ") " +
                        "INSERT INTO {1} (login, email) " +
                        "VALUES ((SELECT login from insert_query), ?)", authenticateTable, mailTable);

        String successfullyString = "The User is successfully inserted!";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(4, user.getEmail());

            preparedStatement.execute();

            return successfullyString;

        } catch (SQLException exception) {
            // I know null return is bad :D
            return null;
        }
    }

    //endregion
}
