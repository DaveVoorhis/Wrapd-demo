package org.reldb.wrapd.demo.mysql;

import org.reldb.wrapd.sqldb.Database;

import java.io.InputStream;
import java.util.Properties;

public class GetDatabase {

	private static final String PROPERTIES_NAME = "db.properties";

	public static Database getDatabase() throws Exception {
		try (InputStream propertiesSource = GetDatabase.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME)) {
			var properties = new Properties();
			if (propertiesSource == null)
				throw new Exception("Missing " + PROPERTIES_NAME);
			properties.load(propertiesSource);
			return new Database(new Pool(
					properties.getProperty("db.url"),
					properties.getProperty("db.user"),
					properties.getProperty("db.password")).getDataSource(),
				properties.getProperty("db.tablename_prefix", ""),
				null
			);
		}
	}
}

