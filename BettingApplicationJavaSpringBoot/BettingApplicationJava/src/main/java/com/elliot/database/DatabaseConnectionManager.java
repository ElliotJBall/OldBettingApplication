package com.elliot.database;

import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;

@Repository
public class DatabaseConnectionManager {

    private static DatabaseConnectionManager instance = null;

    private Connection connection = null;
    private static String dbUrl = "jdbc:mysql://localhost:3306/betting_data", dbUser = "root", dbPassword = "root";

    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
            try {
                getDatabaseDetails();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    /**
     * Retrieve the database connection details from the properties file
     * Should probably check which properties file ect. (Factory pattern to create correct database connection? OR config file to determine which to load?)
     * @throws IOException
     */
    private static void getDatabaseDetails() throws IOException {
/*
 		Properties properties = new Properties();

		// Use ClassLoader so the application can successfully locate the database

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		System.out.println(classLoader.getResource("dbMySql.properties"));
		System.out.println(classLoader.getResource("dbMySql.properties"));

		properties.load(classLoader.getResourceAsStream("dbMySql.properties"));

		dbUrl = properties.getProperty("DB_URL");
		dbUser = properties.getProperty("DB_USER");
		dbPassword = properties.getProperty("DB_PASSWORD");
*/
    }

    /**
     * Retrieve a database connection
     */
    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Given a connection it will then attempt to close it
     * @param connection
     * If it is unable to close the connection then a SQLConnection error is thrown
     */
    public void closeConnection(Connection connection) {
        if (connection == null) {
            throw new NullPointerException();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a String containing a TIMESTAMP of the last time the database was modified
     */
    public String getTimestampOfDatabaseChange() {
        Connection connection = this.getConnection();

        try {
            String query = "SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'betting_data'";

            Statement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            Gson gson = new Gson();
            String latestUpdate = "";
            if (resultSet.next()) {
                latestUpdate = resultSet.getString(1);
            }

            return gson.toJson(latestUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
