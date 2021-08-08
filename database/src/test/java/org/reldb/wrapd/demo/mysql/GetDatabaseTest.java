package org.reldb.wrapd.demo.mysql;

import org.junit.jupiter.api.Test;
import org.reldb.wrapd.sqldb.Database;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

public class GetDatabaseTest {

	@Test
	void verifyDatabaseConnectionSuccessful() {
		Database database = null;
		try {
			database = GetDatabase.getDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Unable to connect to database.", e);
		}
	}

}

