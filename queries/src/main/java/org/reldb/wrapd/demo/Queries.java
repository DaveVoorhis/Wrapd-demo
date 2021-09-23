package org.reldb.wrapd.demo;

import org.reldb.wrapd.sqldb.Database;
import org.reldb.wrapd.sqldb.QueryDefiner;

public class Queries extends QueryDefiner {
    /**
     * Create a QueryDefiner, given a Database and the directory where generated classes will be stored.
     *
     * @param database      Database
     * @param codeDirectory Directory for generated classes.
     * @param packageSpec The package, in dotted notation, to which the generated code belongs.
     */
    public Queries(Database database, String codeDirectory, String packageSpec) {
        super(database, codeDirectory, packageSpec);
    }

    public void QueryABC() throws Exception {
        define("ABC", "SELECT * FROM $$ABC");
    }

    public void QueryXYZ() throws Exception {
        define("XYZ", "SELECT * FROM $$XYZ");
    }

    public void QueryABCJoinXYZ() throws Exception {
        define("ABCJoinXYZ", "SELECT * FROM $$ABC, $$XYZ WHERE x = a");
    }

    public void QueryABCJoinXYZWhere() throws Exception {
        define("ABCJoinXYZWhere", "SELECT * FROM $$ABC, $$XYZ WHERE x = a AND x > ? AND x < ?", 2, 5);
    }
}
