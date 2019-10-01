//package com.nahuelpas.cuentabilidad;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//
//import androidx.room.Room;
//import androidx.room.migration.Migration;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
//
//@RunWith(AndroidJUnit4.class)
//public class MigrationTest {
//
//    private static final String TEST_DB = "migration-test";
//
//    @Rule
//    public MigrationTestHelper helper;
//
//    public MigrationTest() {
//        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
//                MigrationDb.class.getCanonicalName(),
//                new FrameworkSQLiteOpenHelperFactory());
//    }
//
//    @Test
//    public void migrate1To2() throws IOException {
//        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);
//
//        // db has schema version 1. insert some data using SQL queries.
//        // You cannot use DAO classes because they expect the latest schema.
//        db.execSQL(...);
//
//        // Prepare for the next version.
//        db.close();
//
//        // Re-open the database with version 2 and provide
//        // MIGRATION_1_2 as the migration process.
//        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2);
//
//        // MigrationTestHelper automatically verifies the schema changes,
//        // but you need to validate that the data was migrated properly.
//    }
//
//    @Test
//    public void migrateAll() throws IOException {
//        // Create earliest version of the database.
//        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);
//        db.close();
//
//        // Open latest version of the database. Room will validate the schema
//        // once all migrations execute.
//        AppDatabase appDb = Room.databaseBuilder(
//                InstrumentationRegistry.getInstrumentation().getTargetContext(),
//                AppDatabase.class,
//                TEST_DB)
//                .addMigrations(ALL_MIGRATIONS).build()
//        appDb.getOpenHelper().getWritableDatabase();
//        appDb.close();
//    }
//
//    // Array of all migrations
//    private static final Migration[] ALL_MIGRATIONS = new Migration[]{
//            MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4};
//}
