package org.reldb.wrapd.demo;

import org.reldb.toolbox.utilities.Directory;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.sqldb.Definer;

public class Definitions {

    public static void main(String[] args) throws Throwable {
        var db = GetDatabase.getDatabase();
        var codeDirectory = "../application/src/main/java";
        var codePackage = "org.reldb.wrapd.demo.generated";
        if (!Directory.chkmkdir(codeDirectory)) {
            System.out.println("ERROR creating code directory " + codeDirectory);
            return;
        }
        var sqlDefinitions = new Definer(db, codeDirectory, codePackage);
        sqlDefinitions.purgeTarget();
        sqlDefinitions.define("querydefinitions.yaml");
        sqlDefinitions.emitDatabaseAbstractionLayer("DatabaseAbstractionLayer");
        System.out.println("OK: Queries are ready.");
    }

}
