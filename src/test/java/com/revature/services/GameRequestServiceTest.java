package com.revature.services;

import com.uni.daos.GameRequestDAO;
import com.uni.entities.GameRequest;
import com.uni.services.GameRequestImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameRequestServiceTest {
    @Mock
    private GameRequestDAO gameRequestDAOMock;
    @InjectMocks
    private GameRequestImpl gameRequestService = new GameRequestImpl(gameRequestDAOMock);

    private List<GameRequest> mockGameRequests = Stream.of(
            new GameRequest(1, 1, 5, "Main Campus Gym: Court 1", "season 1"),
            new GameRequest(2, 2, 3, "Main Campus Gym: Court 2", "season 1"),
            new GameRequest(3, 3, 6, "Main Campus Gym: Court 1", "season 2"),
            new GameRequest(4, 4, 1, "Satellite Campus Gym: Smith Field", "season 2")
    ).collect(Collectors.toList());

    @DisplayName("Create request")
    @Test
    public void createRequest(){
        GameRequest test = new GameRequest(5, 1, 5, "Main Campus Gym: Court 1", "season 1");
        when(gameRequestDAOMock.save(test)).thenReturn(test);
        GameRequest actual = gameRequestService.createRequest(test);
        assertEquals(test, actual);
        //verify(gameRequestDAOMock).save(any(GameRequest.class));
    }

    // not finished yet
    @DisplayName("Delete request")
    @Test
    public void deleteRequest(){
        when(gameRequestDAOMock.delete(5,5)).thenReturn(true);
        gameRequestService.deleteRequest(5,5);
        //verify(gameRequestDAOMock).delete(anyInt(), anyInt());
    }

    @DisplayName("Get all games and referees")
    @Test
    public void getAllGamesAndReferees(){
        when(gameRequestDAOMock.findAll()).thenReturn(mockGameRequests);
        List<GameRequest> actual = gameRequestService.getAllGamesAndReferees();
        assertEquals(mockGameRequests, actual);
    }
}