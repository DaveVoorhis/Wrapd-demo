package org.reldb.wrapd.demo.mysql;

import org.reldb.wrapd.sqldb.Database;

import java.sql.SQLException;

public class GetDatabase {

	public static Database getDatabase() throws SQLException {
		return new Database(
				Configuration.dbURL,
				Configuration.dbUser,
				Configuration.dbPassword,
				Configuration.dbTablenamePrefix,
				null
		);
	}

}

