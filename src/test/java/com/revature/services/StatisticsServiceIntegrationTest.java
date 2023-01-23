package com.revature.services;

import com.uni.daos.StatBasketballDAO;
import com.uni.daos.UserDAO;
import com.uni.datautils.ConnectionUtil;
import com.uni.dtos.PlayerCard;
import com.uni.entities.StatBasketball;
import com.uni.services.StatisticsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsServiceIntegrationTest {
    private StatisticsServiceImpl statisticsService;
    private StatBasketballDAO statBasketballDAO;

    private UserDAO userDAO;

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

    @BeforeEach
    public void doasAndService(){
        // DAOs
        userDAO = UserDAO.getSingleton();
        statBasketballDAO = StatBasketballDAO.getSingleton();

        //Service
        statisticsService = new StatisticsServiceImpl(statBasketballDAO, userDAO);
    }
    @Test
    void connection_test(){
        System.out.println(ConnectionUtil.getConnection()); // Testing the connection
    }
    @DisplayName("Get players card by userId")
    @Test
    void getPlayerCardByUserId(){

        // When//
        PlayerCard PC = statisticsService.getPlayerCardByUserId(1);

        //Then
        assertEquals(PC.getBasketballStats().get(0).getTeamName(),"Warriors");
    }
    @DisplayName("Get all Basketball by game ID")
    @Test
    void getAllBasketballStatsByGameId(){
        //When
        List<StatBasketball> sbb = statBasketballDAO.findAllByGameId(501);

        //Then
        sbb.forEach(s -> assertEquals(s.getUserId(), 1));
        assertEquals(sbb.get(0).getTeamName(),"Warriors");
    }
    @DisplayName("Update Basketball Statistics")
    @Test
    void UpdateBasketballStat(){
        //Given
        StatBasketball statChange = new StatBasketball(
                1,
                1,
                501,
                "Warriors",
                65,
                12,
                5,
                5
        );
        //When
        StatBasketball existing = statBasketballDAO.findAll().stream().filter(s ->
                s.getUserId() == statChange.getUserId() &&
                        s.getGameId() == statChange.getGameId() &&
                        s.getTeamName().equals(statChange.getTeamName())).findFirst().get();

        //Then
        existing.setPoints(statChange.getPoints());
        existing.setFouls(statChange.getFouls());
        statBasketballDAO.update(existing);
        assertEquals(65, existing.getPoints());
        assertEquals(5, existing.getFouls());
    }

    @DisplayName("Add Basketball Statistics")
    @Test
    void addBasketballStat(){
        //Given
        StatBasketball newStat = new StatBasketball(
                106,
                2,
                502,
                "Gladiators",
                82,
                10,
                6,
                10
        );

        //When
        StatBasketball checking = statBasketballDAO.findAll().stream().filter(s ->
                s.getUserId() != newStat.getUserId() &&
                        s.getGameId() != newStat.getGameId() ||
                        s.getTeamName().equals(newStat.getTeamName())).findFirst().get();

        assertNotEquals(checking.getStatBasketballId(),106);
        //Then
        statBasketballDAO.save(newStat);
        assertEquals(statBasketballDAO.findAll().get(1).getTeamName(), "Gladiators");

    }
}
