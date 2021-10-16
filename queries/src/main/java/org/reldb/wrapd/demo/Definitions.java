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
        purgeTarget();

        defineTable("$$ABC");
        defineTable("$$XYZ", "x = {xValue}", 22);
        defineQueryForTable("SelectABCWhere", "$$ABC", "SELECT * FROM $$ABC WHERE a = {aValue}", 22);
        defineQuery("JoinABCXYZ", "SELECT * FROM $$ABC, $$XYZ WHERE x = a");
        defineQuery("JoinABCXYZWhere", "SELECT * FROM $$ABC, $$XYZ WHERE x = a AND x > {lower} AND x < {higher}", 2, 5);
        defineUpdate("ClearABC", "DELETE FROM $$ABC");
        defineUpdate("ClearXYZ", "DELETE FROM $$XYZ");
        defineUpdate("ClearABCWhere", "DELETE FROM $$ABC WHERE a = {aValue}", 3);
        defineValueOf("ValueOfABCb", "SELECT b FROM $$ABC");
        defineValueOf("ValueOfXYZz", "SELECT z FROM $$XYZ WHERE x = {xValue}", 33);

        emitDatabaseAbstractionLayer("DatabaseAbstractionLayer");
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
