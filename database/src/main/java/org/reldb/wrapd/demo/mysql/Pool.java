package org.reldb.wrapd.demo.mysql;

import com.mchange.v2.c3p0.DataSources;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Pool {
    private final DataSource dataSource;

    public Pool(String dbURL, String dbUser, String dbPassword) throws SQLException {
        var dbProperties = new Properties();
        if (dbUser != null)
            dbProperties.setProperty("user", dbUser);
        if (dbPassword != null)
            dbProperties.setProperty("password", dbPassword);
        // quickly verify database connection
        DriverManager.getConnection(dbURL, dbProperties).close();
        var unpooledSource = DataSources.unpooledDataSource(dbURL, dbProperties);
        dataSource = DataSources.pooledDataSource(unpooledSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
