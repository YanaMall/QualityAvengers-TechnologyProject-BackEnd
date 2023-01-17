package com.revature.services;

import com.uni.daos.TeamDAO;
import com.uni.daos.TeamRequestDAO;
import com.uni.daos.UserDAO;
import com.uni.entities.Team;
import com.uni.services.RegistrationService;
import com.uni.services.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
/*import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;*/

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest
{
    @Mock
    private TeamDAO teamDAOMock;

    @Mock
    private UserDAO userDAOMock;

    @Mock
    private TeamRequestDAO teamRequestDAOMock;

    @InjectMocks
    private RegistrationService registrationService =
            new RegistrationServiceImpl(teamDAOMock, userDAOMock, teamRequestDAOMock);


    /*private List<Team> mockTeams = Stream.of(
            new Team("Grand Dunk Railroad", 5, "active", "basketball"),
            new Team("The Ballers", 6, "active", "basketball"),
            new Team("The Splash", 7, "active", "basketball")
    ).collect(Collectors.toList());*/

    @BeforeEach
    public void setup()
    {
        //another way to set up your mocks
        //registrationService = new RegistrationService(teamDAOMock);
    }

    @DisplayName("Register A Team")
    @Test
    public void registerATeam()
    {
        Team t = new Team("new team A", 14, "active", "basketball");
        when(teamDAOMock.save(t)).thenReturn(t);
        Team testT = registrationService.registerTeam(t);
        assertEquals(t.getName(), testT.getName());
    }
}
