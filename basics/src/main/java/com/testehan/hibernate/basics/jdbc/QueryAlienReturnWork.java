package com.testehan.hibernate.basics.jdbc;

import org.hibernate.jdbc.ReturningWork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryAlienReturnWork implements ReturningWork {

    private final Long alienId;

    public QueryAlienReturnWork(Long alienId) {
        this.alienId = alienId;
    }

    @Override
    public Object execute(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;

        Alien alien = new Alien();
        try {

            statement = connection.prepareStatement("select * from Alien where ID = ?");
            statement.setLong(1, alienId);

            result = statement.executeQuery();
            while (result.next()) {
                String alienName = result.getString("name");
                alien.setName(alienName);
            }
        } finally {
            //   You don’t have to close the connection when you’re done
            if (result != null)
                result.close();
            if (statement != null)
                statement.close();
        }

        return alien;
    }
}
