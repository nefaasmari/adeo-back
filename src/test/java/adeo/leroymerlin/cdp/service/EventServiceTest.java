package adeo.leroymerlin.cdp.service;

import adeo.leroymerlin.cdp.entity.Event;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEvents() {
        Event event1 = new Event(1L, "Event 1", "imgUrl1", new HashSet<>(), 5, "Great event");
        Event event2 = new Event(2L, "Event 2", "imgUrl2", new HashSet<>(), 4, "Awesome event");

        List<Event> expectedEvents = Arrays.asList(event1, event2);

        when(eventRepository.findAllBy()).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.getEvents();

        Assertions.assertEquals(expectedEvents, actualEvents);
        verify(eventRepository, times(1)).findAllBy();
    }

    @Test
    public void testDelete() {
        Long eventId = 1L;

        doNothing().when(eventRepository).deleteById(eventId);

        eventService.delete(eventId);

        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    public void testFindById() {
        Long eventId = 1L;

        Event expectedEvent = new Event(eventId, "Event 1", "imgUrl1", new HashSet<>(), 5, "Great event");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEvent));

        Optional<Event> actualEvent = eventService.findById(eventId);

        Assertions.assertEquals(expectedEvent, actualEvent.get());
        verify(eventRepository, times(1)).findById(eventId);
    }

}
