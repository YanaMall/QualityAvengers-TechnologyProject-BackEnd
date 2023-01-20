package com.revature.services;

import com.uni.daos.GameDAO;
import com.uni.daos.SeasonDAO;
import com.uni.daos.VenueDAO;
import com.uni.datautils.ConnectionUtil;
import com.uni.entities.Game;
import com.uni.entities.Season;
import com.uni.entities.Venue;
import com.uni.services.SchedulingServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SchedulingIntegrationTest {

    private SchedulingServiceImpl schedulingService;
    private VenueDAO venueDAO;
    private GameDAO gameDAO;
    private SeasonDAO seasonDAO;

    @BeforeEach
    public void injectDao(){
        venueDAO = VenueDAO.getSingleton();
        gameDAO = GameDAO.getSingleton();
        seasonDAO = SeasonDAO.getSingleton();
        schedulingService = new SchedulingServiceImpl(venueDAO,gameDAO,seasonDAO);
    }
    @BeforeEach
    public void populateDatabase() throws SQLException {
        try (Connection conn = ConnectionUtil.getConnection()) {
            ConnectionUtil.populateH2Database(conn);
        }
    }

    @AfterEach
    public void clearDatabase() throws SQLException {
        try (Connection conn = ConnectionUtil.getConnection()) {
            ConnectionUtil.clearH2Database(conn);
        }
    }

    @DisplayName("Integration Get all venues")
    @Test
    public void getAllVenues(){
//        Venue testVenue = new Venue("Main Campus Gym");
//        venueDAO.save(testVenue);
        List<Venue> actual = schedulingService.getAllVenues();
        assertEquals(5, actual.size());
    }

    @DisplayName("Integration Get all Games")
    @Test
    public void getAllGames(){
//        Game testGame = new Game(3,"Satellite Campus Gym: Lee Field","Fall 2022 Regular Season Basketball","The Ballers","Grand Dunk Railroad",4,7,1669122000,"pending");
//        //added testGame to the games in DB
//        gameDAO.save(testGame);
        List<Game> actual = schedulingService.getAllGames();
        assertEquals(1, actual.size());
    }

    @DisplayName("Integration Get all Seasons")
    @Test
    public void getAllSeasons(){
//        Season testSeason = new Season("test season");
//        //seasonDAO.save(testSeason);
        List<Season> actual = schedulingService.getAllSeasons();
        assertEquals(3, actual.size());
    }


    @DisplayName("Integration Schedule Game")
    @Test
    public void scheduleGame(){
        Game testGame = new Game(3,"Satellite Campus Gym: Lee Field","Fall 2022 Regular Season Basketball","The Ballers","Grand Dunk Railroad",4,7,1669122000,"pending");
        //gameDAO.save(testGame);
        Game actualGame = schedulingService.scheduleGame(testGame);
        assertEquals("pending", actualGame.getOutcome());
    }

    @DisplayName("Integration schedule season")
    @Test
    public void scheduleSeason(){
        Season testSeason = new Season("test season");
        //seasonDAO.save(testSeason);
        Season newSeason = schedulingService.scheduleSeason(testSeason);
        assertEquals("test season", newSeason.getTitle());
    }

}
