package com.revature.services;

import com.uni.daos.TeamDAO;
import com.uni.daos.TeamRequestDAO;
import com.uni.daos.UserDAO;
import com.uni.dtos.LoginCredentials;
import com.uni.entities.ImUser;
import com.uni.entities.Team;
import com.uni.entities.TeamRequest;
import com.uni.services.RegistrationService;
import com.uni.services.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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


    private List<Team> mockTeams = Stream.of(
            new Team("Grand Dunk Railroad", 5, "active", "basketball"),
            new Team("The Ballers", 6, "active", "basketball"),
            new Team("The Splash", 7, "active", "basketball")
    ).collect(Collectors.toList());

    private List<ImUser> mockTeamUsers = Stream.of(
            new ImUser(5,"Bobby202", "pass123",
                    "player", 72, 195, "none", true),
            new ImUser(6,"Bobby", "npass123",
                    "player", 62, 156, "none", false),
            new ImUser(7,"Bboy", "newpass123",
                    "player", 99, 230, "none", true)
    ).collect(Collectors.toList());

    private List<TeamRequest> mockTeamRequests = Stream.of(
            new TeamRequest(1, "Grand Dunk Railroad", 8, "accepted"),
            new TeamRequest(2, "Grand Dunk Railroad", 12, "accepted"),
            new TeamRequest(5, "Grand Dunk Railroad", 13, "accepted"),
            new TeamRequest(4, "The Ballers", 9, "accepted"),
            new TeamRequest(3, "The Splash", 24, "accepted"),
            new TeamRequest(6, "Grand Dunk Railroad", 10, "pending"),
            new TeamRequest(7, "The Ballers", 11, "pending"),
            new TeamRequest(8, "The Splash", 14, "pending")
    ).collect(Collectors.toList());

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
        //a team to pass into the mock
        Team t = new Team("new team A", 14, "active", "basketball");
        when(teamDAOMock.save(t)).thenReturn(t);
        //the actual method being tested
        Team testT = registrationService.registerTeam(t);
        //assert the mock and the actual are equal
        assertEquals(t.getName(), testT.getName());
    }

    @DisplayName("Get All Teams")
    @Test
    public void getAllTeams()
    {
        when(teamDAOMock.findAll()).thenReturn(mockTeams);
        List<Team> actualTeams = registrationService.getAllTeams();
        assertEquals(mockTeams.size(), actualTeams.size());
    }

    @DisplayName("Get User From Login Credentials")
    @Test
    public void getUserFromLoginCredentials()
    {
        //Both the mock and the registration service need a LoginCredential
        LoginCredentials lc = new LoginCredentials("Bobby202", "pass123");

        //a user to pas into the userDAOMock
        ImUser newUser = new ImUser(5,"Bobby202", "pass123",
                "player", 72, 195, "none", true);
        when(userDAOMock.getByUsername(lc.getUsername())).thenReturn(newUser);

        //getting the username from the method being tested
        String actualUsername = registrationService.getUserFromLoginCredentials(lc).getUsername();
        //assert the mock and the actual are equal
        assertEquals(newUser.getUsername(), actualUsername);
    }

    @DisplayName("Register A User")
    @Test
    public void registerUser()
    {
        ImUser newUser = new ImUser(5,"Bobby202", "pass123",
                "player", 72, 195, "none", true);
        //lenient().when(userDAOMock.save(newUser)).thenReturn(newUser);

        //registrationService.registerUser(newUser);
        assertEquals(userDAOMock.save(newUser), registrationService.registerUser(newUser));
    }

    @DisplayName("Update A User")
    @Test
    public void updateUser()
    {
        ImUser newUser = new ImUser(5,"Bobby202", "newpass123",
                "player", 72, 195, "none", true);
        String passwordBeforeUpdate = "newpass123";
        doNothing().when(userDAOMock).update(newUser);
        userDAOMock.update(newUser);
        registrationService.updateUser(newUser);
        assertEquals(passwordBeforeUpdate, newUser.getPassword());
    }

    @DisplayName("Update A Users' Role")
    @Test
    public void updateUserRole()
    {
        ImUser newUser = new ImUser(5,"Bobby202", "newpass123",
                "referee", 72, 195, "none", true);
        String roleBeforeUpdate = "referee";
        doNothing().when(userDAOMock).updateRole(newUser.getUserId(), "player");
        userDAOMock.updateRole(newUser.getUserId(), "player");
        registrationService.updateRole(newUser.getUserId(), "player");
        //an attempt at upddating a users' role is not successful
        //is it my logic, or does the functionality not work correctly?
        assertNotEquals(roleBeforeUpdate, newUser.getRole());
    }

    @DisplayName("Filter Team Requests By Player")
    @Test
    public void filterTeamRequestsByPlayer()
    {
        ImUser newUser = new ImUser(8,"eegdell0", "DyAU3y5hLA",
                "player", 57, 61, "none", true);
        when(teamRequestDAOMock.findAll()).thenReturn(mockTeamRequests);
        List<TeamRequest> actualTeamRequests = registrationService.filterTeamRequestsByPlayer(newUser.getUserId());
        assertEquals(mockTeamRequests.get(0), actualTeamRequests.get(0));
    }

    @DisplayName("Get Team By Team Name")
    @Test
    public void getTeamByTeamName()
    {
        when(teamDAOMock.findAll()).thenReturn(mockTeams);
        Team gdr = registrationService.getTeamByTeamName("Grand Dunk Railroad");
        assertEquals(mockTeams.get(0).getName(), gdr.getName());
    }

    @DisplayName("Retrieve Players By Team")
    @Test
    public void retrievePlayersByTeam()
    {
        Team t = new Team("New Team", 32, "basketball", "inactive");
        when(userDAOMock.retrieveUserByTeam(t.getName())).thenReturn(mockTeamUsers);
        List<ImUser> actualTeamUsers = registrationService.retrievePlayersByTeam(t.getName());
        assertEquals(mockTeamUsers, actualTeamUsers);
    }

    @DisplayName("Retrieve All Users")
    @Test
    public void retrieveAllUsers()
    {
        when(userDAOMock.findAll()).thenReturn(mockTeamUsers);
        List<ImUser> allUsers = registrationService.retrieveAllUsers();
        assertEquals(mockTeamUsers, allUsers);
    }

    @DisplayName("Get All Team Requests")
    @Test
    public void getAllTeamRequests()
    {
        when(teamRequestDAOMock.findAll()).thenReturn(mockTeamRequests);
        List<TeamRequest> allRequests = registrationService.getAllTeamRequests();
        assertEquals(mockTeamRequests, allRequests);
    }

    @DisplayName("Filter Team Requests By Team")
    @Test
    public void filterTeamRequestsByTeam()
    {
        when(teamRequestDAOMock.findAll()).thenReturn(mockTeamRequests);
        List<TeamRequest> allTeamRequests = registrationService.filterTeamRequestsByTeam("Grand Dunk Railroad");
        for (TeamRequest tr: allTeamRequests)
        {
            assertEquals("Grand Dunk Railroad", tr.getTeamName());
        }
    }

    @DisplayName("Create A Request")
    @Test
    public void createRequest()
    {
        TeamRequest tr = new TeamRequest(8, "The Splash", 14, "pending");
        when(teamRequestDAOMock.save(tr)).thenReturn(tr);
        TeamRequest realTR = registrationService.createRequest(tr);
        assertEquals(tr, realTR);
    }

    @DisplayName("Approve A Request")
    @Test
    public void approveRequest()
    {
        TeamRequest tr = new TeamRequest(8, "The Splash", 14, "pending");
        when(teamRequestDAOMock.findAll()).thenReturn(mockTeamRequests);
        TeamRequest realTR = registrationService.approveRequest(tr.getTeamRequestId());
        assertEquals("accepted", realTR.getTeamRequestStatus());
    }

    @DisplayName("Deny A Request")
    @Test
    public void denyRequest()
    {
        TeamRequest tr = new TeamRequest(8, "The Splash", 14, "pending");
        when(teamRequestDAOMock.findAll()).thenReturn(mockTeamRequests);
        TeamRequest realTR = registrationService.denyRequest(tr.getTeamRequestId());
        assertEquals("denied", realTR.getTeamRequestStatus());
    }
}
