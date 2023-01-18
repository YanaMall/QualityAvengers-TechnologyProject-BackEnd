package com.revature.services;

import com.uni.daos.StatBasketballDAO;
import com.uni.daos.UserDAO;
import com.uni.dtos.PlayerCard;
import com.uni.entities.ImUser;
import com.uni.entities.StatBasketball;
import com.uni.services.StatisticsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class StatisticsServiceTest {

    @Mock
    private UserDAO userDAOMock;
    @Mock
    private StatBasketballDAO statBasketballDAOMock;

    @InjectMocks
    private StatisticsServiceImpl statisticsServiceImpl = new StatisticsServiceImpl(statBasketballDAOMock, userDAOMock);
    // Mock ImUser data
    private final List<ImUser> mockImUser = Stream.of(
            new ImUser(1, "user1", "1234", "player", 72, 165, "https://robohash.org/nequeestaccusantium.png", false),
            new ImUser(2, "user2", "1234", "player", 70, 160, "https://robohash.org/nequeestaccusantium.png", false),
            new ImUser(3, "user3", "1234", "player", 75, 170, "https://robohash.org/nequeestaccusantium.png", true),
            new ImUser(4, "user4", "1234", "player", 69, 155, "https://robohash.org/nequeestaccusantium.png", false),
            new ImUser(5, "user5", "1234", "player", 80, 180, "https://robohash.org/nequeestaccusantium.png", true)

    ).collect(Collectors.toList());

    // Mocked StatBasketball data
    private final List<StatBasketball> mockStatBasketballs = Stream.of(
            new StatBasketball(101,1,501, "Warriors",55,12,5,3),
            new StatBasketball(102,2,502, "Challengers",50,17,7,5),
            new StatBasketball(103,3,503, "Rockers",67,10,2,2),
            new StatBasketball(104,4,504, "RedBulls",55,11,5,3),
            new StatBasketball(105,5,505, "BlueWhales",47,17,8,8)
    ).collect(Collectors.toList());


    @Test
    public void getPlayserCardByUserId(){
        when(userDAOMock.findById(3)).thenReturn(mockImUser.get(2)); //If userDAOMock finds id=3, then return mockdata userid = 3
        when(statBasketballDAOMock.findAll()).thenReturn(mockStatBasketballs); // if statBasketballDAOMock finds all then return all mocking data
        PlayerCard pc = statisticsServiceImpl.getPlayerCardByUserId(3);
        assertEquals(pc.getUsername(), "user3"); //asserting values
        assertEquals(pc.getHeightInches(), 75);
        assertEquals(pc.getWeightLbs(), 170);
        assertEquals(pc.getProfilePic(), "https://robohash.org/nequeestaccusantium.png");
        assertTrue(pc.isHideBiometrics());
    }
}


