//package com.revature.services;
//
//import com.uni.dtos.PlayerCard;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//@DataJ
//public class StatisticsServiceIntegrationTest {
//
//    @DisplayName("Get players card by userId")
//    @Test
//    public void getPlayerCardByUserId(){
//        when(userDAO.findById(3)).thenReturn(ImUser.get(2)); //If userDAOMock finds id=3, then return mockImUser userid = 3 which is get(2)
//        when(statBasketballDAO.findAll()).thenReturn(); // if statBasketballDAOMock finds all then return all mocking data
//        PlayerCard pc = statisticsServiceImpl.getPlayerCardByUserId(3);
//        assertEquals(pc.getUsername(), "user3");
//        assertEquals(pc.getHeightInches(), 75);
//        assertEquals(pc.getWeightLbs(), 170);
//        assertEquals(pc.getProfilePic(), "url3");
//        assertTrue(pc.isHideBiometrics());
//    }
//
//
//}
