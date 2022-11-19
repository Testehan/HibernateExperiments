package com.testehan.hibernate.basics.jdbc;

import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryAlienWork implements Work {


    private final Long alienId;

    public QueryAlienWork(Long alienId) {
        this.alienId = alienId;
    }

    @Override
    public void execute(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
        try
        {
            statement = connection.prepareStatement("select * from Alien where ID = ?");
            statement.setLong(1, alienId);

            result = statement.executeQuery();
            System.out.println("Alien names are :");
            while (result.next()) {
                String alienName = result.getString("name");
                System.out.println(alienName);
            }
        } finally {
                //   You don’t have to close the connection when you’re done

            if (result != null)
                result.close();
            if (statement != null)
                statement.close();
        }
    }
}
