package assignment;

import lombok.extern.slf4j.Slf4j;


import java.sql.*;
import java.sql.DriverManager;

@Slf4j
class EventRepository {

    private Connection connection;

    EventRepository() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
            createTableIfNotExists(connection);
            connection.commit();
        } catch (ClassNotFoundException | SQLException exc) {
            log.error("Problem with connection to hsql database occurred", exc);
        }
    }


    void insert(EventEntity entity) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT  INTO EVENTS1 values ("
                    + entity.getId() + ", "
                    + entity.getEventDuration() + ", "
                    + entity.getHost() + ", "
                    + entity.getType() + ", "
                    + entity.isAlert() + "); ");
            connection.commit();
            stmt.close();
            log.info("Persisting new event {}", entity.getId());
        } catch (SQLException e) {
            log.error("Could not insert {} into table", entity.getId(), e);
        }

    }

    private void createTableIfNotExists(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS EVENTS1( " +
                "id varchar(32) NOT NULL, " +
                "eventduration INT NOT NULL, " +
                "host varchar(225), " +
                "type varchar(16), " +
                "alert boolean NOT NULL)");
        if (ps.executeUpdate() != 0) {
            log.info("Table EVENTS created");
        }
        ps.close();
    }


    void closeConnection() {

        try {
            if (connection != null)
                connection.close();
        } catch (SQLException sqle) {
            log.error("Could not close connection: ", sqle);
        }
    }
}
