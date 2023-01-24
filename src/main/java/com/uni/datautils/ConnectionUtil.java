package com.uni.datautils;

import com.uni.exceptions.DatabaseConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    public static Connection getConnection() {
        try {
            InputStream input = ConnectionUtil.class.getClassLoader().getResourceAsStream("database.properties");
            Properties prop = new Properties();
            prop.load(input);
            String testMode = prop.getProperty("test-mode");

            // If running tests, use H2 in-memory database, otherwise, use Postgres database
            if (testMode.equals("true")) {
                Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

                return conn;
            } else {
                //example connection string jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password
                Connection conn = DriverManager.getConnection(
                            System.getenv("URL"),
                            System.getenv("username"),
                            System.getenv("password")
                );

                return conn;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    public static void populateH2Database(Connection conn) {
        String imRoleSql = "create type im_role as enum ('referee','player','admin')";



        String userTableSql = "create table im_user(\n" +
                "    user_id serial primary key,\n" +
                "    username varchar unique,\n" +
                "    password varchar,\n" +
                "    role im_role,\n" +
                "    height int, -- centimeters\n" +
                "    weight int, -- kilograms\n" +
                "    profile_pic varchar,\n" +
                "    display_biometrics bool\n" +
                ")";

        String insertUserSql = "insert into im_user (username, password, role, height, weight, profile_pic, display_biometrics) values (?, ?, ?::im_role, ?, ?, ?, ?)";

        String statBasketballSql = "create table stat_basketball(\n " +
                " s_basketball_id serial, \n" +
                " user_id int, \n" +
                " game_id int, \n" +
                " team_name varchar, \n" +
                " points int, \n" +
                " rebounds int, \n" +
                " assists int, \n" +
                " fouls int "+
                ")";
        String insertStatBasketball = "insert into stat_basketball (user_id, game_id, team_name, points, rebounds, assists, fouls) values(?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps1 = conn.prepareStatement(imRoleSql); // Create im_role enum
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(userTableSql); // Create im_user table
            ps2.executeUpdate();

            PreparedStatement ps6 = conn.prepareStatement(statBasketballSql);
            ps6.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement(insertUserSql);

            PreparedStatement ps7 = conn.prepareStatement(insertStatBasketball);

            // Create statbasketball
            ps7.setInt(1, 1);
            ps7.setInt(2, 501);
            ps7.setString(3, "Warriors");
            ps7.setInt(4,55);
            ps7.setInt(5, 12);
            ps7.setInt(6,5);
            ps7.setInt(7, 3);
            ps7.executeUpdate();
            // Create a user
            ps3.setString(1, "testing123");
            ps3.setString(2, "12345");
            ps3.setString(3, "player");
            ps3.setInt(4, 70);
            ps3.setInt(5, 150);
            ps3.setString(6, null);
            ps3.setBoolean(7, true);
            ps3.executeUpdate();

            PreparedStatement ps4 = conn.prepareStatement(insertUserSql); // Create a user
            ps4.setString(1, "gatorFan99");
            ps4.setString(2, "testpassword");
            ps4.setString(3, "admin");
            ps4.setInt(4, 64);
            ps4.setInt(5, 135);
            ps4.setString(6, null);
            ps4.setBoolean(7, false);
            ps4.executeUpdate();

            PreparedStatement ps5 = conn.prepareStatement(insertUserSql); // Create a user
            ps5.setString(1, "john_doe");
            ps5.setString(2, "astroswon");
            ps5.setString(3, "referee");
            ps5.setInt(4, 73);
            ps5.setInt(5, 190);
            ps5.setString(6, null);
            ps5.setBoolean(7, true);
            ps5.executeUpdate();

            PreparedStatement ps8= conn.prepareStatement(insertUserSql); // Create a user
            ps8.setString(1, "ayianatesting");
            ps8.setString(2, "54321");
            ps8.setString(3, "player");
            ps8.setInt(4, 58);
            ps8.setInt(5, 270);
            ps8.setString(6, null);
            ps8.setBoolean(7, true);
            ps8.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sportSql = "create type sport as enum ('softball','basketball');";
        String teamStatusSql = "create type team_status as enum ('active','inactive','suspended');";
        String teamTableSql = "create table team(\n" +
                "    name varchar primary key,\n" +
                "    captain int references im_user(user_id),\n" +
                "    team_status team_status,\n" +
                "    sport sport\n" +
                ");";
        String insertTeamSql = "insert into team values(?, ?, ?, ?);";
        try
        {
            PreparedStatement sportStatement = conn.prepareStatement(sportSql);// create sport enum
            sportStatement.executeUpdate();
            PreparedStatement statusStatement = conn.prepareStatement(teamStatusSql);// create team status enum
            statusStatement.executeUpdate();
            PreparedStatement statement = conn.prepareStatement(teamTableSql); //create team table
            statement.executeUpdate();
            PreparedStatement insertStatement = conn.prepareStatement(insertTeamSql); //insert team into table
            insertStatement.setString(1, "Grand Dunk Railroad");
            insertStatement.setInt(2, 1);
            insertStatement.setString(3, "active");
            insertStatement.setString(4, "basketball");
            insertStatement.executeUpdate();
            PreparedStatement insertStatement2 = conn.prepareStatement(insertTeamSql); //insert team into table
            insertStatement2.setString(1, "The Ballers");
            insertStatement2.setInt(2, 4);
            insertStatement2.setString(3, "active");
            insertStatement2.setString(4, "basketball");
            insertStatement2.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        String teamRequestEnum = "create type team_request_status as enum ('pending','denied','accepted');";
        String teamRequestSql = "create table team_requests(\n" +
                "\trequest_id serial primary key,\n" +
                "\tteam varchar references team(name),\n" +
                "\tuser_id int references im_user(user_id),\n" +
                "\tstatus team_request_status\n" +
                ");";
        String insertTeamRequest = "insert into team_requests values (default,?,?,?::team_request_status)";
        try
        {
            PreparedStatement trEnum = conn.prepareStatement(teamRequestEnum);
            trEnum.executeUpdate();
            PreparedStatement createTRTable = conn.prepareStatement(teamRequestSql);
            createTRTable.executeUpdate();
            PreparedStatement insertTR = conn.prepareStatement(insertTeamRequest);
            insertTR.setString(1, "Grand Dunk Railroad");
            insertTR.setInt(2, 4);
            insertTR.setString(3, "pending");
            insertTR.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        //lei populating venues, games and season in the h2 db

        String createSeasonTableSql = "create table season(\n" +
                "    title varchar primary key\n" +
                ");";
        String venuesTableSql = "create table venue(\n" +
                "    title varchar primary key\n" +
                ");";
        String gameTableSql = "create table game(\n" +
                "    game_id serial primary key,\n" +
                "    venue varchar references venue(title),\n" +
                "    season varchar references season(title),\n" +
                "    home_team varchar references team(name),\n" +
                "    away_team varchar references team(name),\n" +
                "    home_score int,\n" +
                "    away_score int,\n" +
                "    game_start int,\n" +
                "    game_outcome varchar\n" +
                ");";
        String createTeamSql = "create table team(\n" +
                " name varchar primary key,\n" +
                " captain int references im_user(user_id),\n" +
                " team_status varchar,\n" +
                " sport varchar\n" +
                ")";
        String insertTeamSql2 = "insert into team values('Grand Dunk Railroad',1, 'active','basketball');\n" +
                "insert into team values('The Ballers',2, 'active','basketball');\n" +
                "insert into team values('The Splash',3, 'active','basketball');";
        String insertVenueSql = "insert into venue values ('Main Campus Gym: Court 1');\n" +
                "insert into venue values ('Main Campus Gym: Court 2');\n" +
                "insert into venue values ('Main Campus Gym: Court 3');\n" +
                "insert into venue values ('Satellite Campus Gym: Smith Field');\n" +
                "insert into venue values ('Satellite Campus Gym: Lee Field');";
        String gameOutcome = "create type game_outcome as enum ('scheduled','cancelled','pending')";
        String insertGameSql = "insert into game values (default, ?, ?, ?, ?, ?, ?, ?, ?::game_outcome)";

        String insertSeasonSql = "insert into season values ('Fall 2022 Regular Season Basketball');\n" +
                "insert into season values ('Fall 2022 Regular Season Softball');\n" +
                "insert into season values ('Hopping for a Cure Charity Tournament');";

        try {
            PreparedStatement ps11 = conn.prepareStatement(gameOutcome);
            ps11.executeUpdate();
            PreparedStatement ps1 = conn.prepareStatement(createSeasonTableSql); // Create season
            ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement(insertSeasonSql); // insert season
            ps2.executeUpdate();
            //PreparedStatement ps3 = conn.prepareStatement(createTeamSql); // create team
            //ps3.executeUpdate();
            //PreparedStatement ps4 = conn.prepareStatement(insertTeamSql2); // insert team
            //ps4.executeUpdate();
            PreparedStatement ps5 = conn.prepareStatement(venuesTableSql); // Create venues table
            ps5.executeUpdate();
            PreparedStatement ps6 = conn.prepareStatement(insertVenueSql); // insert venues
            ps6.executeUpdate();
            PreparedStatement ps8 = conn.prepareStatement(gameTableSql); // create games
            ps8.executeUpdate();
            PreparedStatement ps7 = conn.prepareStatement(insertGameSql); // insert game
            ps7.setString(1,"Main Campus Gym: Court 2");
            ps7.setString(2,"Fall 2022 Regular Season Basketball");
            ps7.setString(3,"The Ballers");
            ps7.setString(4, "Grand Dunk Railroad");
            ps7.setInt(5,0);
            ps7.setInt(6,0);
            ps7.setInt(7,1669122000);
            ps7.setString(8,"scheduled");
            ps7.executeUpdate();
            PreparedStatement insertGame = conn.prepareStatement(insertGameSql); // insert game
            insertGame.setString(1,"Main Campus Gym: Court 1");
            insertGame.setString(2,"Fall 2022 Regular Season Basketball");
            insertGame.setString(3,"The Ballers");
            insertGame.setString(4, "Grand Dunk Railroad");
            insertGame.setInt(5,0);
            insertGame.setInt(6,0);
            insertGame.setInt(7,1669122000);
            insertGame.setString(8,"scheduled");
            insertGame.executeUpdate();
            PreparedStatement insertGame2 = conn.prepareStatement(insertGameSql); // insert game
            insertGame2.setString(1,"Main Campus Gym: Court 2");
            insertGame2.setString(2,"Fall 2022 Regular Season Basketball");
            insertGame2.setString(3,"Grand Dunk Railroad");
            insertGame2.setString(4, "The Ballers");
            insertGame2.setInt(5,0);
            insertGame2.setInt(6,0);
            insertGame2.setInt(7,1669122000);
            insertGame2.setString(8,"scheduled");
            insertGame2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String gameRequestTbl = "create table game_requests( game_request_id serial primary key, game int references game(game_id), user_id int references im_user(user_id));";
        String insertGameRequestSQL = "insert into game_requests values(default, 3, 1);";
        String newInsertSQL = "insert into game_requests values (default, ?, ?)";
        try
        {
            PreparedStatement createTable = conn.prepareStatement(gameRequestTbl);
            createTable.executeUpdate();
            PreparedStatement insertGameRequest = conn.prepareStatement(insertGameRequestSQL);
            insertGameRequest.executeUpdate();
            PreparedStatement newInsert = conn.prepareStatement(newInsertSQL);
            newInsert.setInt(1, 2);
            newInsert.setInt(2, 2);
            newInsert.executeUpdate();
            PreparedStatement newInsert2 = conn.prepareStatement(newInsertSQL);
            newInsert2.setInt(1, 3);
            newInsert2.setInt(2, 3);
            newInsert2.executeUpdate();
            PreparedStatement newInsert3 = conn.prepareStatement(newInsertSQL);
            newInsert3.setInt(1, 3);
            newInsert3.setInt(2, 3);
            newInsert3.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public static void clearH2Database(Connection conn) {
        String sql = "DROP ALL OBJECTS";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
