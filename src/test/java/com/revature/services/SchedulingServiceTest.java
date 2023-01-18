package com.revature.services;

import com.uni.daos.GameDAO;
import com.uni.daos.SeasonDAO;
import com.uni.daos.VenueDAO;
import com.uni.entities.Venue;
import com.uni.services.SchedulingService;
import com.uni.services.SchedulingServiceImpl;
import io.cucumber.java.sl.In;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SchedulingServiceTest {

    @Mock
    private VenueDAO venueDAOMock;
    @Mock
    private GameDAO gameDAOMock;
    @Mock
    private SeasonDAO seasonDAOMock;
    @InjectMocks
    private SchedulingService schedulingService = new SchedulingServiceImpl(venueDAOMock,gameDAOMock,seasonDAOMock);

    private List<Venue> mockVenues = Stream.of(
            new Venue("Stade"),
            new Venue("terrain"),
            new Venue("desert")
    ).collect(Collectors.toList());
    @DisplayName("Get all venues")
    @Test
    public void getAllVenues(){
        when(venueDAOMock.findAll()).thenReturn(mockVenues);
        List<Venue> actual = schedulingService.getAllVenues();
        assertEquals(3, actual.size());

    }

}
