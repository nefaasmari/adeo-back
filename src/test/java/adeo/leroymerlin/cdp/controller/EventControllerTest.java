package adeo.leroymerlin.cdp.controller;

import adeo.leroymerlin.cdp.entity.Event;
import adeo.leroymerlin.cdp.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    private EventController eventController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        eventController = new EventController(eventService);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testFindEvents() throws Exception {

        List<Event> events = mockEvents();

        when(eventService.getEvents()).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(events.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(events.get(1).getId()));

        verify(eventService, times(1)).getEvents();
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void testFindEventsWithQuery() throws Exception {

        List<Event> events = mockEvents();
        String query = "Event";

        when(eventService.getFilteredEvents(query)).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/search/{query}", query)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(events.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(events.get(1).getId()));

        verify(eventService, times(1)).getFilteredEvents(query);
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void testDeleteEvent() throws Exception {
        Long eventId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/events/{id}", eventId))
                .andExpect(status().isOk());

        verify(eventService, times(1)).delete(eventId);
        verifyNoMoreInteractions(eventService);
    }

    public List<Event> mockEvents() {

        List<Event> eventList = new ArrayList<>();

        Event event1 = new Event();
        event1.setId(1L);
        event1.setTitle("title1");
        event1.setComment("comment1");
        event1.setBands(null);
        event1.setNbStars(1);

        Event event2 = new Event();
        event2.setId(2L);
        event1.setTitle("title2");
        event2.setComment("comment1");
        event2.setBands(null);
        event2.setNbStars(2);

        eventList.add(event1);
        eventList.add(event1);

        return eventList;

    }
}
