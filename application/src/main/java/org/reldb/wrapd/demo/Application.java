package org.reldb.wrapd.demo;

import org.reldb.wrapd.demo.generated.*;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.sqldb.Database;

import java.sql.SQLException;

public class Application {

    public static void populateABC(Database database) throws Exception {
        for (var i = 1000; i < 1010; i++) {
            var tuple = new ABCTuple(database);
            tuple.a = i;
            tuple.b = i * 2;
            tuple.c = Integer.toString(i * 10);
            tuple.insert();
        }
    }

    public static void populateXYZ(Database database) throws Exception {
        for (var i = 1005; i < 1015; i++) {
            var tuple = new XYZTuple(database);
            tuple.x = i;
            tuple.y = i * 2;
            tuple.z = Integer.toString(i * 100);
            tuple.insert();
        }
    }

    public static void main(String[] args) throws Exception {
        var database = GetDatabase.getDatabase();
        if (args.length == 1 && args[0].equals("test"))
            return;
        System.out.println("== ClearABC ==");
        ClearABC.update(database);
        System.out.println("== ClearXYZ ==");
        ClearXYZ.update(database);
        System.out.println("== populateABC ==");
        populateABC(database);
        System.out.println("== populateXYZ ==");
        populateXYZ(database);
        System.out.println("== ABC ==");
        ABC.query(database)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
        System.out.println("== XYZ (1007) ==");
        XYZ.query(database, 1007)
                .forEach(row -> System.out.println("Row: x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== ClearABCWhere (1007) ==");
        ClearABCWhere.update(database, 1007);
        System.out.println("== ABC ==");
        ABC.query(database)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
        System.out.println("== ABC - queryForUpdate row.b += 100 ==");
        ABC.queryForUpdate(database)
                .forEach(row -> {
                    row.b += 100;
                    try {
                        row.update();
                    } catch (SQLException e) {
                        System.out.println("Row update failed due to: " + e);
                    }
                });
        System.out.println("== ABC ==");
        ABC.query(database)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
        System.out.println("== ABCJoinXYZ ==");
        ABCJoinXYZ.query(database)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                        " x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== ABCJoinXYZWhere (1002, 1008) ==");
        ABCJoinXYZWhere.query(database, 1002, 1008)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                        " x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== ValueOfABCb ==");
        System.out.println(ValueOfABCb.valueOf(database).get());
        System.out.println("== ValueOfXYZz ==");
        System.out.println(ValueOfXYZz.valueOf(database, 1007).get());
    }

}
