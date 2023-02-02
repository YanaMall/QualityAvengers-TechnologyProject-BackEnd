package com.revature.services;

import com.uni.daos.TeamDAO;
import com.uni.daos.TeamRequestDAO;
import com.uni.daos.UserDAO;
import com.uni.dtos.LoginCredentials;
import com.uni.entities.ImUser;
import com.uni.entities.Team;
import com.uni.datautils.ConnectionUtil;
import com.uni.entities.TeamRequest;
import com.uni.services.RegistrationService;
import com.uni.services.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class RegistrationServiceIntegrationTest
{
    /*private RegistrationService registrationService;
    private TeamDAO teamDAO;
    private UserDAO userDAO;
    private TeamRequestDAO teamRequestDAO;

    @BeforeEach
    public void injectDAO()
    {
        teamDAO = TeamDAO.getSingleton();
        userDAO = UserDAO.getSingleton();
        teamRequestDAO = TeamRequestDAO.getSingleton();
        registrationService = new RegistrationServiceImpl(teamDAO, userDAO, teamRequestDAO);
    }

    @BeforeEach
    public void populateDatabase() throws SQLException
    {
        try (Connection conn = ConnectionUtil.getConnection())
        {
            ConnectionUtil.populateH2Database(conn);
        }
    }

    @AfterEach
    public void clearDatabase() throws SQLException
    {
        try (Connection conn = ConnectionUtil.getConnection())
        {
            ConnectionUtil.clearH2Database(conn);
        }
    }

    @DisplayName("Register A User")
    @Test
    public void registerUser()
    {
        ImUser testUser = new ImUser(1,"ayiana", "passing",
                "player", 58, 270, "none", true);
        ImUser newUser = registrationService.registerUser(testUser);
        assertEquals(testUser, newUser);
    }

    @DisplayName("Register A Team")
    @Test
    public void registerATeam()
    {
        Team t = new Team("New Team A", 3, "basketball", "active");

        Team testT = registrationService.registerTeam(t);
        //assert the mock and the actual are equal
        assertEquals(t.getName(), testT.getName());
    }

    @DisplayName("Get All Teams")
    @Test
    public void getAllTeams()
    {
        int numTeams = 2;
        List<Team> actualTeams = registrationService.getAllTeams();

        assertEquals(2, actualTeams.size());
    }

    @DisplayName("Get User From Login Credentials")
    @Test
    public void getUserFromLoginCredentials()
    {
        //The registration service needs a LoginCredential
        LoginCredentials lc = new LoginCredentials("ayianatesting", "54321");

        ImUser loggedInUser = registrationService.getUserFromLoginCredentials(lc);
        String actualUsername = registrationService.getUserFromLoginCredentials(lc).getUsername();
        //assert the mock and the actual are equal
        assertEquals(loggedInUser.getUsername(), actualUsername);
    }

    @DisplayName("Update A User")
    @Test
    public void updateUser()
    {
        LoginCredentials lc = new LoginCredentials("testing123", "12345");
        ImUser User = registrationService.getUserFromLoginCredentials(lc);
        String nonUpdatedUsername = User.getUsername();
        //I'm unsure about passing in a new user as a way to update a user
        //Does this change the user entirely?
        User = registrationService.updateUser(new ImUser(1,"Bobby202", "12345",
                "player", 72, 195, "none", true));
        String updatedUsername = User.getUsername();
        assertNotEquals(nonUpdatedUsername, updatedUsername);
    }

    @DisplayName("Update A Users' Role")
    @Test
    public void updateUserRole()
    {
        LoginCredentials lc = new LoginCredentials("john_doe", "astroswon");
        ImUser User = registrationService.getUserFromLoginCredentials(lc);
        String roleBeforeUpdate = "referee";

        registrationService.updateRole(User.getUserId(), "player");
        //an attempt at updating a users' role is not successful
        //is it my logic, or does the functionality not work correctly?
        assertNotEquals(roleBeforeUpdate, User.getRole());
    }

    @DisplayName("Filter Team Requests By Player")
    @Test
    public void filterTeamRequestsByPlayer()
    {
        List<TeamRequest> allTeamRequests = registrationService.filterTeamRequestsByPlayer(4);
        for (TeamRequest tr: allTeamRequests)
        {
            assertEquals(4, tr.getRequesterId());
        }
    }

    @DisplayName("Get Team By Team Name")
    @Test
    public void getTeamByTeamName()
    {
        Team gdr = registrationService.getTeamByTeamName("Grand Dunk Railroad");
        assertEquals("Grand Dunk Railroad", gdr.getName());
    }

    @DisplayName("Retrieve Players By Team")
    @Test
    public void retrievePlayersByTeam()
    {
        //There is only one person on this team in the memory database
        List<ImUser> actualTeamUsers = registrationService.retrievePlayersByTeam("The Ballers");
        assertEquals(1,  actualTeamUsers.size());
    }

    @DisplayName("Retrieve All Users")
    @Test
    public void retrieveAllUsers()
    {
        List<ImUser> allUsers = userDAO.findAll();
        List<ImUser> allUsersRS = registrationService.retrieveAllUsers();
        assertEquals(allUsers.size(), allUsersRS.size());
    }

    @DisplayName("Get All Team Requests")
    @Test
    public void getAllTeamRequests()
    {
        //There is only one team request in the memory database
        List<TeamRequest> allRequests = teamRequestDAO.findAll();
        List<TeamRequest> allRequestsRS = registrationService.getAllTeamRequests();
        assertEquals(allRequests.size(), allRequestsRS.size());
    }

    @DisplayName("Filter Team Requests By Team")
    @Test
    public void filterTeamRequestsByTeam()
    {
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
        TeamRequest tr = new TeamRequest(2, "The Ballers", 2, "pending");
        TeamRequest realTR = registrationService.createRequest(tr);
        assertEquals(tr, realTR);
    }

    @DisplayName("Approve A Request")
    @Test
    public void approveRequest()
    {
        TeamRequest tr = new TeamRequest(1, "The Ballers", 4, "pending");
        TeamRequest realTR = registrationService.approveRequest(tr.getTeamRequestId());
        assertEquals("accepted", realTR.getTeamRequestStatus());
    }

    @DisplayName("Deny A Request")
    @Test
    public void denyRequest()
    {
        TeamRequest tr = new TeamRequest(1, "The Ballers", 4, "pending"); 
        TeamRequest realTR = registrationService.denyRequest(tr.getTeamRequestId());
        assertEquals("denied", realTR.getTeamRequestStatus());
    }*/
}
