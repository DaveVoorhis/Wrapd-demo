package org.reldb.wrapd.demo;

import org.reldb.toolbox.progress.ConsoleProgressIndicator;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.response.Response;
import org.reldb.wrapd.response.Result;
import org.reldb.wrapd.schema.SQLSchema;
import org.reldb.wrapd.sqldb.Database;

import java.sql.SQLException;

public class Schema extends SQLSchema {
    /**
     * Create an instance of a schema for a specified Database.
     *
     * @param database Database.
     */
    public Schema(Database database) {
        super(database);
    }

    @Override
    protected Update[] getUpdates() {
        return new Update[] {
                schema -> {
                    getDatabase().updateAll("CREATE TABLE $$tester01 (x INT NOT NULL PRIMARY KEY, y INT NOT NULL)");
                    return Result.OK;
                },
                schema -> {
                    getDatabase().updateAll("CREATE TABLE $$tester02 (a INT NOT NULL PRIMARY KEY, b INT NOT NULL)");
                    return Result.OK;
                },
                schema -> {
                    getDatabase().updateAll("ALTER TABLE $$tester01 ADD z VARCHAR(20)");
                    getDatabase().updateAll("ALTER TABLE $$tester02 ADD c VARCHAR(40)");
                    getDatabase().updateAll("RENAME TABLE $$tester01 TO $$XYZ");
                    getDatabase().updateAll("RENAME TABLE $$tester02 TO $$ABC");
                    return Result.OK;
                }
        };
    }

    public static void main(String[] args) {
        Schema schema;
        try {
            schema = new Schema(GetDatabase.getDatabase());
        } catch (Exception e) {
            Response.printError("ERROR in Schema: main: GetDatabase.getDatabase():", e);
            return;
        }
        var result = schema.setup(new ConsoleProgressIndicator());
        if (result.isOk())
            System.out.println("OK: Schema has been set up.");
        else {
            Response.printError("ERROR in Schema: Schema creation:", result.error);
        }
    }
}
