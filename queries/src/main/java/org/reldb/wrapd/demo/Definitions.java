package org.reldb.wrapd.demo;

import org.reldb.toolbox.utilities.Directory;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.response.Response;
import org.reldb.wrapd.sqldb.Database;
import org.reldb.wrapd.sqldb.Definer;
import org.reldb.wrapd.sqldb.UpdateDefinition;

public class Definitions extends Definer {

    public Definitions(Database database, String codeDirectory, String packageSpec) {
        super(database, codeDirectory, packageSpec);
    }

    public void QueryABC() throws Exception {
        defineQuery("ABC", "SELECT * FROM $$ABC");
    }

    public void QueryXYZ() throws Exception {
        defineQuery("XYZ", "SELECT * FROM $$XYZ");
    }

    public void QueryABCJoinXYZ() throws Exception {
        defineQuery("ABCJoinXYZ", "SELECT * FROM $$ABC, $$XYZ WHERE x = a");
    }

    public void QueryABCJoinXYZWhere() throws Exception {
        defineQuery("ABCJoinXYZWhere", "SELECT * FROM $$ABC, $$XYZ WHERE x = a AND x > ? AND x < ?", 2, 5);
    }

    public void UpdateClearABC() throws Exception {
        defineUpdate("ClearABC", "DELETE FROM $$ABC");
    }

    public UpdateDefinition UpdateClearXYZ() {
        return new UpdateDefinition("ClearXYZ", "DELETE FROM $$XYZ");
    }

    public void UpdateClearABCWhere() throws Exception {
        defineUpdate("ClearABCWhere", "DELETE FROM $$ABC WHERE a = ?", 3);
    }

    public static void main(String[] args) {
        Database db;
        try {
            db = GetDatabase.getDatabase();
        } catch (Exception e) {
            Response.printError("ERROR in Queries: main: GetDatabase.getDatabase():", e);
            return;
        }
        var codeDirectory = "src/main/java";
        var codePackage = "org.reldb.wrapd.demo.generated";
        if (!Directory.chkmkdir(codeDirectory)) {
            System.out.println("ERROR creating code directory " + codeDirectory);
            return;
        }
        var sqlDefinitions = new Definitions(db, codeDirectory, codePackage);
        try {
            sqlDefinitions.generate();
        } catch (Definer.DefinerException e) {
            Response.printError("ERROR in Queries: main: queryDefinitions.generate():", e);
        }
        System.out.println("OK: Queries are ready.");
    }

}
