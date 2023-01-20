package com.revature.services;

import com.uni.daos.StatBasketballDAO;
import com.uni.daos.UserDAO;
import com.uni.dtos.PlayerCard;
import com.uni.entities.ImUser;
import com.uni.entities.StatBasketball;
import com.uni.services.StatisticsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

public class StatisticsServiceTest {

    @Mock
    private static UserDAO userDAOMock;
    @Mock
    private static StatBasketballDAO statBasketballDAOMock;

    @InjectMocks
    private StatisticsServiceImpl statisticsServiceImpl = new StatisticsServiceImpl(statBasketballDAOMock, userDAOMock);

    static List<ImUser> mockImUser = Stream.of(
            new ImUser(1, "user1", "1234", "player", 72, 165, "url1", false),
            new ImUser(2, "user2", "1234", "player", 70, 160, "url2", false),
            new ImUser(3, "user3", "1234", "player", 75, 170, "url3", true),
            new ImUser(4, "user4", "1234", "player", 69, 155, "url4", false),
            new ImUser(5, "user5", "1234", "player", 80, 180, "url5", true)

    ).collect(Collectors.toList());
    static List<StatBasketball> mockStatBasketballs = Stream.of(
            new StatBasketball(101,1,501, "Warriors",55,12,5,3),
            new StatBasketball(102,2,502, "Challengers",50,17,7,5),
            new StatBasketball(103,3,503, "Rockers",67,10,2,2),
            new StatBasketball(104,4,504, "RedBulls",55,11,5,3),
            new StatBasketball(105,5,505, "BlueWhales",47,17,8,8)
    ).collect(Collectors.toList());
    @DisplayName("Get players card by userId")
    @Test
    public void getPlayerCardByUserId(){
        //I userId =3, then return mockdata 3(get(2) means 3rd data)
        when(userDAOMock.findById(3)).thenReturn(mockImUser.get(2));

        // if statBasketballDAOMock finds all then return all mocking data
        when(statBasketballDAOMock.findAll()).thenReturn(mockStatBasketballs);

        PlayerCard PC3 = statisticsServiceImpl.getPlayerCardByUserId(3);

        assertEquals(PC3.getUsername(), "user3");
        assertEquals(PC3.getHeightInches(), 75);
        assertEquals(PC3.getWeightLbs(), 170);
        assertEquals(PC3.getProfilePic(), "url3");
        assertTrue(PC3.isHideBiometrics());
    }

    @DisplayName("Get all Basketball Statistics by game id")
    @Test
    public void getAllBasketballStatsByGameId(){
        //Find gameId then return Mock bascketball data
        when(statBasketballDAOMock.findAllByGameId(502)).thenReturn(mockStatBasketballs);

        List<StatBasketball> sbb = statisticsServiceImpl.getAllBasketballStatsByGameId(502);

        // Verifying number of invocations
        verify(statBasketballDAOMock,times(1)).findAllByGameId(502);

        assertEquals(5, sbb.size());   // Asserting all the details related to gameId=502 is true
    }
    @DisplayName("Update Basketball Statistics")
    @Test
    public void UpdateBasketballStat(){
        //Existing data with new changes
        StatBasketball statChange = new StatBasketball(101,1,501, "Warriors",65,12,5,5);

        when(statBasketballDAOMock.findAll()).thenReturn(mockStatBasketballs);

        StatBasketball updateStat = statisticsServiceImpl.addOrUpdateBasketballStat(statChange);

        // New changes updating
        statBasketballDAOMock.update(updateStat);

        // Verifying new changes by number of invocations
        verify(statBasketballDAOMock, times(2)).update(updateStat);
        assertEquals(65, updateStat.getPoints()); // Asserting new changes
        assertEquals(5, updateStat.getFouls());
    }
    @DisplayName("Add Basketball Statistics")
    @Test
    public void addBasketballStat(){
        // New data not existing
        StatBasketball statChange = new StatBasketball(106,6,506, "Gladiators",82,10,6,10);

        when(statBasketballDAOMock.findAll()).thenReturn(mockStatBasketballs);

        StatBasketball addStat = statisticsServiceImpl.addOrUpdateBasketballStat(statChange);

        // Saving new data
        statBasketballDAOMock.save(addStat);     // Create new one and save

        verify(statBasketballDAOMock, times(1)).save(addStat);
    }
}


