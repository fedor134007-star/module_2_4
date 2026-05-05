package utils;

import org.flywaydb.core.Flyway;

public class DatabaseMigration {
    private static Flyway flyway;

    public static void migrate() {
        if (flyway == null)  createFlyway();
        flyway.migrate();  // выполняет миграции
    }


    public static void cleanDatabase() {
        if (flyway == null)  createFlyway();
        flyway.clean();
    }



    private static void createFlyway() {
        flyway = Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:5432/module_2_4",
                        "fedor",
                        "password"
                )
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .cleanDisabled(false)
                .load();
    }
}
