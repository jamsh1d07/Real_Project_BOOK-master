package uz.pdp.real_project_book;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class RealProjectBookApplication {

    @Value("${spring.sql.init.mode}")
   static  String mode;

    public static void main(String[] args) {
        SpringApplication.run(RealProjectBookApplication.class, args);



        if (mode!=null && mode.equals("always")){
            final String DB_URL = "jdbc:postgresql://localhost:5432/jdbcbook";
            final String USER = "postgres";
            final String PASS = "0706";


            // Open a connection
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE \"Book\"(\n" +
                        "    \"id\" INTEGER NOT NULL ,\n" +
                        "    \"name\" VARCHAR(255) NULL,\n" +
                        "    \"price\" DOUBLE PRECISION NULL,\n" +
                        "    \"code\" UUID NULL,\n" +
                        "    \"category_id\" INTEGER NULL,\n" +
                        "    \"active\" INTEGER NOT NULL\n" +
                        ");\n" +
                        "ALTER TABLE\n" +
                        "    \"Book\" ADD PRIMARY KEY(\"id\");\n" +
                        "ALTER TABLE\n" +
                        "    \"Book\" ADD CONSTRAINT \"book_name_unique\" UNIQUE(\"name\");\n" +
                        "CREATE TABLE \"Category\"(\n" +
                        "    \"id\" INTEGER NULL,\n" +
                        "    \"name\" VARCHAR(255) NULL,\n" +
                        "    \"active\" BOOLEAN NOT NULL\n" +
                        ");\n" +
                        "CREATE INDEX \"category_name_index\" ON\n" +
                        "    \"Category\"(\"name\");\n" +
                        "ALTER TABLE\n" +
                        "    \"Category\" ADD PRIMARY KEY(\"id\");\n" +
                        "ALTER TABLE\n" +
                        "    \"Category\" ADD CONSTRAINT \"category_name_unique\" UNIQUE(\"name\");\n" +
                        "CREATE TABLE \"history\"(\n" +
                        "    \"id\" INTEGER NOT NULL,\n" +
                        "    \"user_id\" INTEGER NOT NULL,\n" +
                        "    \"created_at\" DATE NOT NULL,\n" +
                        "    \"action\" VARCHAR(255) NOT NULL,\n" +
                        "    \"object\" VARCHAR(255) NOT NULL,\n" +
                        "    \"objectName\" VARCHAR(255) NOT NULL\n" +
                        ");\n" +
                        "ALTER TABLE\n" +
                        "    \"history\" ADD PRIMARY KEY(\"id\");\n" +
                        "CREATE TABLE \"users\"(\n" +
                        "    \"id\" INTEGER NOT NULL,\n" +
                        "    \"username\" VARCHAR(255) NULL,\n" +
                        "    \"email\" VARCHAR(255) NULL,\n" +
                        "    \"password\" VARCHAR(255) NULL,\n" +
                        "    \"role\" VARCHAR(255) NOT NULL,\n" +
                        "    \"active\" BOOLEAN NOT NULL\n" +
                        ");\n" +
                        "ALTER TABLE\n" +
                        "    \"users\" ADD PRIMARY KEY(\"id\");\n" +
                        "ALTER TABLE\n" +
                        "    \"users\" ADD CONSTRAINT \"users_email_unique\" UNIQUE(\"email\");\n" +
                        "ALTER TABLE\n" +
                        "    \"Book\" ADD CONSTRAINT \"book_category_id_foreign\" FOREIGN KEY(\"category_id\") REFERENCES \"Category\"(\"id\");\n" +
                        "ALTER TABLE\n" +
                        "    \"history\" ADD CONSTRAINT \"history_user_id_foreign\" FOREIGN KEY(\"user_id\") REFERENCES \"users\"(\"id\");";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }





//        if (mode == ("always")) {
//
//            SingleConnectionDataSource ds = new SingleConnectionDataSource();
//            ds.setDriverClassName("org.postgresql.Driver");
//            ds.setUrl("jdbc:postgresql://localhost:5432/jdbcbook");
//            ds.setUsername("postgres");
//            ds.setPassword("0706");
//
//
//            JdbcTemplate jt = new JdbcTemplate(ds);
//
//
//            jt.execute("CREATE TABLE \"Book\"(\n" +
//                    "    \"id\" INTEGER NOT NULL,\n" +
//                    "    \"name\" VARCHAR(255) NULL,\n" +
//                    "    \"price\" DOUBLE PRECISION NULL,\n" +
//                    "    \"code\" UUID NULL,\n" +
//                    "    \"category_id\" INTEGER NULL\n" +
//                    ");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"Book\" ADD PRIMARY KEY(\"id\");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"Book\" ADD CONSTRAINT \"book_name_unique\" UNIQUE(\"name\");\n" +
//                    "CREATE TABLE \"Category\"(\n" +
//                    "    \"id\" INTEGER NULL,\n" +
//                    "    \"name\" VARCHAR(255) NULL\n" +
//                    ");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"Category\" ADD PRIMARY KEY(\"id\");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"Category\" ADD CONSTRAINT \"category_name_unique\" UNIQUE(\"name\");\n" +
//                    "CREATE TABLE \"history\"(\n" +
//                    "    \"id\" INTEGER NOT NULL,\n" +
//                    "    \"user_id\" INTEGER NOT NULL,\n" +
//                    "    \"created_at\" DATE NOT NULL,\n" +
//                    "    \"action\" VARCHAR(255) NOT NULL,\n" +
//                    "    \"object\" VARCHAR(255) NOT NULL,\n" +
//                    "    \"objectName\" VARCHAR(255) NOT NULL\n" +
//                    ");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"history\" ADD PRIMARY KEY(\"id\");\n" +
//                    "CREATE TABLE \"users\"(\n" +
//                    "    \"id\" INTEGER NOT NULL,\n" +
//                    "    \"username\" VARCHAR(255) NULL,\n" +
//                    "    \"email\" VARCHAR(255) NULL,\n" +
//                    "    \"password\" VARCHAR(255) NULL,\n" +
//                    "    \"role\" VARCHAR(255) NOT NULL,\n" +
//                    "    \"active\" BOOLEAN NOT NULL\n" +
//                    ");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"users\" ADD PRIMARY KEY(\"id\");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"users\" ADD CONSTRAINT \"users_email_unique\" UNIQUE(\"email\");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"Book\" ADD CONSTRAINT \"book_code_foreign\" FOREIGN KEY(\"code\") REFERENCES \"Category\"(\"id\");\n" +
//                    "ALTER TABLE\n" +
//                    "    \"history\" ADD CONSTRAINT \"history_user_id_foreign\" FOREIGN KEY(\"user_id\") REFERENCES \"users\"(\"id\");");
//
//            ds.destroy();
//        }
//
    }

}
