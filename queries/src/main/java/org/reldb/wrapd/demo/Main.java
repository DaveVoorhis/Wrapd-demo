package org.reldb.wrapd.demo;

import org.reldb.toolbox.utilities.Directory;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.response.Response;
import org.reldb.wrapd.sqldb.Database;
import org.reldb.wrapd.sqldb.QueryDefiner;
import org.reldb.wrapd.sqldb.UpdateDefiner;

public class Main {

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
        var queryDefinitions = new Queries(db, codeDirectory, codePackage);
        try {
            queryDefinitions.generate();
        } catch (QueryDefiner.QueryDefinerException e) {
            Response.printError("ERROR in Queries: main: queryDefinitions.generate():", e);
        }
        var updateDefinitions = new Updates(db, codeDirectory, codePackage);
        try {
            updateDefinitions.generate();
        } catch (UpdateDefiner.UpdateDefinerException e) {
            Response.printError("ERROR in Updates: main: updateDefinitions.generate():", e);
        }
        System.out.println("OK: Queries are ready.");
    }

}
