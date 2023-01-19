package dev.tran.integrationtest;

import com.uni.daos.GameDAO;
import com.uni.daos.SeasonDAO;
import com.uni.daos.VenueDAO;
import com.uni.datautils.ConnectionUtil;
import com.uni.entities.Game;
import com.uni.entities.Season;
import com.uni.services.SchedulingServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

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
            ConnectionUtil.;
        }
    }
    @AfterEach
    public void clearDatabase() throws SQLException {
        try (Connection conn = ConnectionUtil.getConnection()) {

        }
    }

    @DisplayName("Scheduling service + DAO")
    @Test
    public void scheduleSeason(){
        Season testSeason = new Season("Season1");
        Season newSeason = schedulingService.scheduleSeason(testSeason);
        assertEquals("Season1", newSeason.getTitle());
    }

}
