package org.reldb.wrapd.demo;

import org.reldb.toolbox.utilities.Directory;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.sqldb.Database;
import org.reldb.wrapd.sqldb.Definer;

public class Definitions extends Definer {

    public Definitions(Database database, String codeDirectory, String packageSpec) {
        super(database, codeDirectory, packageSpec);
    }

    void generate() throws Throwable {
        defineTable("$$ABC");
        defineTable("$$XYZ", "x = ?", 22);
        defineQueryForTable("ABCWhere", "$$ABC", "SELECT * FROM $$ABC WHERE a = ?", 22);
        defineQuery("ABCJoinXYZ", "SELECT * FROM $$ABC, $$XYZ WHERE x = a");
        defineQuery("ABCJoinXYZWhere", "SELECT * FROM $$ABC, $$XYZ WHERE x = a AND x > ? AND x < ?", 2, 5);
        defineUpdate("ClearABC", "DELETE FROM $$ABC");
        defineUpdate("ClearXYZ", "DELETE FROM $$XYZ");
        defineUpdate("ClearABCWhere", "DELETE FROM $$ABC WHERE a = ?", 3);
    }

    public static void main(String[] args) throws Throwable {
        var db = GetDatabase.getDatabase();
        var codeDirectory = "src/main/java";
        var codePackage = "org.reldb.wrapd.demo.generated";
        if (!Directory.chkmkdir(codeDirectory)) {
            System.out.println("ERROR creating code directory " + codeDirectory);
            return;
        }
        var sqlDefinitions = new Definitions(db, codeDirectory, codePackage);
        sqlDefinitions.generate();
        System.out.println("OK: Queries are ready.");
    }

}
