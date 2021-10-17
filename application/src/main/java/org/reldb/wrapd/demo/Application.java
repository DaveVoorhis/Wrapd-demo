package org.reldb.wrapd.demo;

import org.reldb.wrapd.demo.generated.*;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.sqldb.Database;

import java.sql.SQLException;

public class Application {

    public static void populateABC(Database database) throws SQLException {
        for (var i = 1000; i < 1010; i++) {
            var tuple = new ABCTuple(database);
            tuple.a = i;
            tuple.b = i * 2;
            tuple.c = Integer.toString(i * 10);
            tuple.insert();
        }
    }

    public static void populateXYZ(Database database) throws SQLException {
        for (var i = 1005; i < 1015; i++) {
            var tuple = new XYZTuple(database);
            tuple.x = i;
            tuple.y = i * 2;
            tuple.z = Integer.toString(i * 100);
            tuple.insert();
        }
    }

    public static void demo1(Database database) throws Exception {
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
        System.out.println("== JoinABCXYZ ==");
        JoinABCXYZ.query(database)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                        " x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== JoinABCXYZWhere (1002, 1008) ==");
        JoinABCXYZWhere.query(database, 1002, 1008)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                        " x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== ValueOfABCb ==");
        var valueOfABCb = ValueOfABCb.valueOf(database);
        System.out.println(valueOfABCb.isPresent() ? valueOfABCb.get() : "?");
        System.out.println("== ValueOfXYZz ==");
        var valueOfXYZz = ValueOfXYZz.valueOf(database, 1007);
        System.out.println(valueOfXYZz.orElse("?"));
    }

    public static void demo2(Database database) throws Exception {
        var dal = new DatabaseAbstractionLayer(database);
        System.out.println("== ClearABC ==");
        dal.clearABC();
        System.out.println("== ClearXYZ ==");
        dal.clearXYZ();
        System.out.println("== populateABC ==");
        populateABC(database);
        System.out.println("== populateXYZ ==");
        populateXYZ(database);
        System.out.println("== ABC ==");
        dal.aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
        System.out.println("== XYZ (1007) ==");
        dal.xYZ(1007)
                .forEach(row -> System.out.println("Row: x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== ClearABCWhere (1007) ==");
        dal.clearABCWhere(1007);
        System.out.println("== ABC ==");
        dal.aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
        System.out.println("== ABC - queryForUpdate row.b += 100 ==");
        dal.aBCForUpdate()
                .forEach(row -> {
                    row.b += 100;
                    try {
                        row.update();
                    } catch (SQLException e) {
                        System.out.println("Row update failed due to: " + e);
                    }
                });
        System.out.println("== ABC ==");
        dal.aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
        System.out.println("== JoinABCXYZ ==");
        dal.joinABCXYZ()
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                        " x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== JoinABCXYZWhere (1002, 1008) ==");
        dal.joinABCXYZWhere(1002, 1008)
                .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                        " x = " + row.x + " y = " + row.y + " z = " + row.z));
        System.out.println("== ValueOfABCb ==");
        var valueOfABCb = dal.valueOfABCb();
        System.out.println(valueOfABCb.isPresent() ? valueOfABCb.get() : "?");
        System.out.println("== ValueOfXYZz ==");
        var valueOfXYZz = dal.valueOfXYZz(1007);
        System.out.println(valueOfXYZz.orElse("?"));
    }

    private static class Demo3 extends DatabaseAbstractionLayer {

        public Demo3() throws Exception {
            super(GetDatabase.getDatabase());
        }

        public void run() throws Exception {
            System.out.println("== ClearABC ==");
            clearABC();
            System.out.println("== ClearXYZ ==");
            clearXYZ();
            System.out.println("== populateABC ==");
            populateABC(getDatabase());
            System.out.println("== populateXYZ ==");
            populateXYZ(getDatabase());
            System.out.println("== ABC ==");
            aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
            System.out.println("== XYZ (1007) ==");
            xYZ(1007)
                    .forEach(row -> System.out.println("Row: x = " + row.x + " y = " + row.y + " z = " + row.z));
            System.out.println("== ClearABCWhere (1007) ==");
            clearABCWhere(1007);
            System.out.println("== ABC ==");
            aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
            System.out.println("== ABC - queryForUpdate row.b += 100 ==");
            aBCForUpdate()
                    .forEach(row -> {
                        row.b += 100;
                        try {
                            row.update();
                        } catch (SQLException e) {
                            System.out.println("Row update failed due to: " + e);
                        }
                    });
            System.out.println("== ABC ==");
            aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
            System.out.println("== JoinABCXYZ ==");
            joinABCXYZ()
                    .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                            " x = " + row.x + " y = " + row.y + " z = " + row.z));
            System.out.println("== JoinABCXYZWhere (1002, 1008) ==");
            joinABCXYZWhere(1002, 1008)
                    .forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c +
                            " x = " + row.x + " y = " + row.y + " z = " + row.z));
            System.out.println("== ValueOfABCb ==");
            var valueOfABCb = valueOfABCb();
            System.out.println(valueOfABCb.isPresent() ? valueOfABCb.get() : "?");
            System.out.println("== ValueOfXYZz ==");
            System.out.println(valueOfXYZz(1007).orElse("?"));
        }
    }

    public static void main(String[] args) throws Exception {
        var database = GetDatabase.getDatabase();
        if (args.length == 1 && args[0].equals("test"))
            return;
        System.out.println("-------------------- Demo 1 --------------------");
        demo1(database);
        System.out.println("-------------------- Demo 2 --------------------");
        demo2(database);
        System.out.println("-------------------- Demo 3 --------------------");
        new Demo3().run();
    }

}
