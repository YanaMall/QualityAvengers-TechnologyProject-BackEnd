package com.revature.services;

import com.uni.daos.GameRequestDAO;
import com.uni.datautils.ConnectionUtil;
import com.uni.entities.Game;
import com.uni.entities.GameRequest;
import com.uni.services.GameRequestImpl;
import com.uni.services.GameRequestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GameRequestServiceIntegrationTest {
    private GameRequestImpl gameRequestService;
    private GameRequestDAO gameRequestDAO;

    @BeforeEach
    public void injectDAO(){
        gameRequestDAO = GameRequestDAO.getSingleton();
        gameRequestService = new GameRequestImpl(gameRequestDAO);
    }

    @BeforeEach
    public void populateDatabase() throws SQLException {
        try(Connection conn = ConnectionUtil.getConnection()){
            ConnectionUtil.populateH2Database(conn);
        }
    }

    @AfterEach
    public void clearDatabase() throws SQLException{
        try(Connection conn = ConnectionUtil.getConnection()){
            ConnectionUtil.clearH2Database(conn);
        }
    }

    @DisplayName("Create request")
    @Test
    public void createRequest(){
        GameRequest test = new GameRequest(5, 1, 5, "Main Campus Gym: Court 1", "season 1");
        
    }

    // not finished yet
    @DisplayName("Delete request")
    @Test
    public void deleteRequest(){
        gameRequestDAO.delete(5,5);
        //
    }

    @DisplayName("Get all games and referees")
    @Test
    public void getAllGamesAndReferees(){
        List<GameRequest> actual = gameRequestService.getAllGamesAndReferees();
        assertEquals(4, actual.size());
    }


}
