package org.reldb.wrapd.demo;

import org.reldb.wrapd.demo.generated.*;
import org.reldb.wrapd.demo.mysql.GetDatabase;
import org.reldb.wrapd.response.Result;

import java.sql.SQLException;

public class Application {

    private static class Demo extends DatabaseAbstractionLayer {

        public Demo() throws Exception {
            super(GetDatabase.getDatabase());
        }

        void populateABC() throws SQLException {
            for (var i = 1000; i < 1010; i++) {
                var tuple = new ABCTuple(getDatabase());
                tuple.a = i;
                tuple.b = i * 2;
                tuple.c = Integer.toString(i * 10);
                tuple.insert();
            }
        }

        void populateXYZ() throws SQLException {
            for (var i = 1005; i < 1015; i++) {
                var tuple = new XYZTuple(getDatabase());
                tuple.x = i;
                tuple.y = i * 2;
                tuple.z = Integer.toString(i * 100);
                tuple.insert();
            }
        }

        void run() throws Exception {
            System.out.println("== ClearABC ==");
            clearABC();

            System.out.println("== ClearXYZ ==");
            clearXYZ();

            System.out.println("== populateABC ==");
            populateABC();

            System.out.println("== populateXYZ ==");
            populateXYZ();

            System.out.println("== ABC ==");
            aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));

            System.out.println("== XYZ (1007) ==");
            xYZ(1007)
                    .forEach(row -> System.out.println("Row: x = " + row.x + " y = " + row.y + " z = " + row.z));

            // Demonstrate (and hopefully commit) transaction.
            System.out.println("------------ start transaction ------------");
            var result1 = getDatabase().processTransaction(xact -> {
                System.out.println("== ClearABCWhere (1007) ==");
                clearABCWhere(xact, 1007);
                System.out.println("== ABC ==");
                aBC(xact).forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
                System.out.println("== ABC - queryForUpdate row.b += 100 ==");
                aBCForUpdate(xact)
                        .forEach(row -> {
                            row.b += 100;
                            try {
                                row.update(xact);
                            } catch (SQLException e) {
                                System.out.println("Row update failed due to: " + e);
                                throw new RuntimeException(e);
                            }
                        });
                return Result.OK;
            });
            System.out.println(result1.isValid()
                    ? "------------ commit transaction ------------"
                    : "------------ abort transaction ------------"
            );

            System.out.println("== ABC ==");
            aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));

            // As above, but abort transaction
            System.out.println("------------ start transaction ------------");
            var result2 = getDatabase().processTransaction(xact -> {
                System.out.println("== ClearABCWhere (1007) ==");
                clearABCWhere(xact, 1007);
                System.out.println("== ABC ==");
                aBC(xact).forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));
                System.out.println("== ABC - queryForUpdate row.b += 100 ==");
                aBCForUpdate(xact)
                        .forEach(row -> {
                            row.b += 100;
                            try {
                                if (row.a == 1008)
                                    throw new SQLException("Force failure to abort transaction.");
                                row.update(xact);
                            } catch (SQLException e) {
                                System.out.println("Row update failed due to: " + e);
                                throw new RuntimeException(e);
                            }
                        });
                return Result.OK;
            });
            System.out.println(result2.isValid()
                    ? "------------ commit transaction ------------"
                    : "------------ abort transaction ------------"
            );

            System.out.println("== ABC ==");
            aBC().forEach(row -> System.out.println("Row: a = " + row.a + " b = " + row.b + " c = " + row.c));

            System.out.println("== JoinABCXYZ ==");
            joinABCXYZ()
                    .forEach(row -> System.out.println("Row:" +
                            " a = " + row.a + " b = " + row.b + " c = " + row.c +
                            " x = " + row.x + " y = " + row.y + " z = " + row.z));

            System.out.println("== JoinABCXYZWhere (1002, 1008) ==");
            joinABCXYZWhere(1002, 1008)
                    .forEach(row -> System.out.println("Row:" +
                            " a = " + row.a + " b = " + row.b + " c = " + row.c +
                            " x = " + row.x + " y = " + row.y + " z = " + row.z));

            System.out.println("== ValueOfABCb ==");
            var valueOfABCb = valueOfABCb();
            System.out.println(valueOfABCb.isPresent() ? valueOfABCb.get() : "?");

            System.out.println("== ValueOfXYZz ==");
            System.out.println(valueOfXYZz(1007).orElse("?"));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1 && args[0].equals("test"))
            return;
        new Demo().run();
    }

}
