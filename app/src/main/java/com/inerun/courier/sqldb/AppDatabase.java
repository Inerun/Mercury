package com.inerun.courier.sqldb;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by vineet on 9/11/2017.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "SNSDatabase";

    public static final int VERSION = 1;

//
//    @Migration(version = VERSION, database = AppDatabase.class)
//    public static class OrderTableMigration extends AlterTableMigration<Orders> {
//        IProperty property;
//
//        public OrderTableMigration(Class<Orders> table) {
//            super(table);
//        }
//
//
//        @Override
//        public void onPreMigrate() {
//            super.onPreMigrate();
//
//            addColumn(SQLiteType.INTEGER, "sync_status");
//
//
//        }
//
//        @Override
//        public void onPostMigrate() {
//            super.onPostMigrate();
//
//        }
//    }
//
//    @Migration(version = VERSION, database = AppDatabase.class)
//    public static class OrderDetailTableMigration extends AlterTableMigration<Orders_Details> {
//        IProperty property;
//
//        public OrderDetailTableMigration(Class<Orders_Details> table) {
//            super(table);
//        }
//
//
//        @Override
//        public void onPreMigrate() {
//            super.onPreMigrate();
//
//            addColumn(SQLiteType.INTEGER, "sync_status");
//
//
//        }
//
//
//    }
//
//    @Migration(version = VERSION, database = AppDatabase.class)
//    public static class PatientTableMigration extends AlterTableMigration<Patient> {
//        IProperty property;
//
//        public PatientTableMigration(Class<Patient> table) {
//            super(table);
//        }
//
//
//        @Override
//        public void onPreMigrate() {
//            super.onPreMigrate();
//
//            addColumn(SQLiteType.INTEGER, "sync_status");
//
//
//        }
//
//        @Override
//        public void onPostMigrate() {
//            super.onPostMigrate();
//
////            SQLite.update(Orders_Details.class).
//
//        }
//
//
//    }
//

}