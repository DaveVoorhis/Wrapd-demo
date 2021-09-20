package org.reldb.wrapd.demo;

import org.reldb.toolbox.utilities.Directory;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.response.Response;
import org.reldb.wrapd.sqldb.Database;
import org.reldb.wrapd.sqldb.QueryDefiner;
import org.reldb.wrapd.sqldb.QueryDefinition;

import java.sql.SQLException;

public class Queries extends QueryDefiner {
    /**
     * Create a QueryDefiner, given a Database and the directory where Tuple-derived classes will be stored.
     *
     * @param database      Database
     * @param codeDirectory Directory for Tuple-derived classes.
     * @param packageSpec The package, in dotted notation, to which the generated code belongs.
     */
    public Queries(Database database, String codeDirectory, String packageSpec) {
        super(database, codeDirectory, packageSpec);
    }

    public QueryDefinition QueryDefinition01() {
        return new QueryDefinition("Query01", "SELECT * FROM $$tester01 WHERE x > ? AND x < ?", 3, 7);
    }

    public QueryDefinition QueryDefinition02() {
        return new QueryDefinition("Query02", "SELECT * FROM $$tester01");
    }

    public void QueryDefinition03() throws Exception {
        define("Query03", "SELECT * FROM $$tester01 WHERE x > ?", 3);
    }

    public void queryDefinition04() throws Exception {
        define("Query04", "SELECT x FROM $$tester01");
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
        var queryDefinitions = new Queries(db, codeDirectory, codePackage);
        try {
            queryDefinitions.generate();
        } catch (QueryDefinerException e) {
            Response.printError("ERROR in Queries: main: queryDefinitions.generate():", e);
        }
        System.out.println("OK: Queries are ready.");
    }
}
