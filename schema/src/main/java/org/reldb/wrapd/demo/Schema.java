package org.reldb.wrapd.demo;

import org.reldb.toolbox.progress.ConsoleProgressIndicator;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.response.Response;
import org.reldb.wrapd.schema.SQLSchemaYAML;

public class Schema {
    public static void main(String[] args) throws Throwable {
        var schema = new SQLSchemaYAML(GetDatabase.getDatabase(), "schema.yaml");
        var result = schema.setup(new ConsoleProgressIndicator());
        if (result.isOk())
            System.out.println("OK: Schema has been set up.");
        else
            Response.printError("ERROR in Schema set up.", result.error);
    }
}
