package org.reldb.wrapd.demo;

import org.reldb.wrapd.sqldb.Database;
import org.reldb.wrapd.sqldb.UpdateDefiner;
import org.reldb.wrapd.sqldb.UpdateDefinition;

public class Updates extends UpdateDefiner {
    /**
     * Create an UpdateDefiner, given a Database and the directory where generated classes will be stored.
     *
     * @param database      Database
     * @param codeDirectory Directory for generated classes.
     * @param packageSpec The package, in dotted notation, to which the generated code belongs.
     */
    public Updates(Database database, String codeDirectory, String packageSpec) {
        super(database, codeDirectory, packageSpec);
    }

    public void UpdateClearABC() throws Exception {
        define("ClearABC", "DELETE FROM $$ABC");
    }

    public UpdateDefinition UpdateClearXYZ() {
        return new UpdateDefinition("ClearXYZ", "DELETE FROM $$XYZ");
    }

    public void UpdateClearABCWhere() throws Exception {
        define("ClearABCWhere", "DELETE FROM $$ABC WHERE a = ?", 3);
    }
}
