package org.reldb.wrapd.demo;

import org.reldb.wrapd.demo.generated.Query01Tuple;
import org.reldb.wrapd.demo.generated.Query04;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.response.Response;

import java.sql.SQLException;

public class Application {

    public static void populate() throws Exception {
        var database = GetDatabase.getDatabase();
        for (var x = 1000; x < 1010; x++) {
            var tuple = new Query01Tuple();
            tuple.x = x;
            tuple.y = x * 2;
            tuple.insert(database, "$$tester01");
        }
    }

    public static void main(String[] args) {
            try {
                populate();
            } catch (Throwable t) {
                Response.printError("ERROR populating db:", t);
            }
            try {
                var db = GetDatabase.getDatabase();
                Query04.query(db)
                        .forEach(row -> System.out.println("Row: x = " + row.x));
            } catch (Throwable t) {
                Response.printError("ERROR querying db:", t);
            }
    }

}
