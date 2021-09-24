package org.reldb.wrapd.demo;

import org.reldb.toolbox.utilities.Directory;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.sqldb.Database;
import org.reldb.wrapd.sqldb.Definer;
import org.reldb.wrapd.sqldb.UpdateDefinition;

public class Definitions extends Definer {

    public Definitions(Database database, String codeDirectory, String packageSpec) {
        super(database, codeDirectory, packageSpec);
    }

    public void QueryABC() throws Throwable {
        defineQuery("ABC", "SELECT * FROM $$ABC");
    }

    public void QueryXYZ() throws Throwable {
        defineQuery("XYZ", "SELECT * FROM $$XYZ");
    }

    public void QueryABCJoinXYZ() throws Throwable {
        defineQuery("ABCJoinXYZ", "SELECT * FROM $$ABC, $$XYZ WHERE x = a");
    }

    public void QueryABCJoinXYZWhere() throws Throwable {
        defineQuery("ABCJoinXYZWhere", "SELECT * FROM $$ABC, $$XYZ WHERE x = a AND x > ? AND x < ?", 2, 5);
    }

    public void UpdateClearABC() throws Throwable {
        defineUpdate("ClearABC", "DELETE FROM $$ABC");
    }

    public UpdateDefinition UpdateClearXYZ() {
        return new UpdateDefinition("ClearXYZ", "DELETE FROM $$XYZ");
    }

    public void UpdateClearABCWhere() throws Throwable {
        defineUpdate("ClearABCWhere", "DELETE FROM $$ABC WHERE a = ?", 3);
    }

    public static void main(String[] args) throws DefinerException, Exception {
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
