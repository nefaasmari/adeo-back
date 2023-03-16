package adeo.leroymerlin.cdp.service;

import adeo.leroymerlin.cdp.entity.Event;
import adeo.leroymerlin.cdp.repository.EventRepository;
import adeo.leroymerlin.cdp.entity.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String query) {

        /*** Stream ***/
        /*List<Event> events = eventRepository.findAllBy();
        return events.stream()
                .filter(event -> event.getBands().stream()
                        .anyMatch(band -> band.getMembers().stream()
                                .anyMatch(member -> member.getName().contains(query))))
                .collect(Collectors.toList());*/

        /*** Stream with count ***/

        List<Event> events = eventRepository.findAllBy();
        return events.stream()
                .map(event -> {
                    Set<Band> filteredBands = event.getBands().stream()
                            .filter(band -> band.getMembers().stream()
                                    .anyMatch(member -> member.getName().contains(query)))
                            .map(band -> {
                                int memberCount = (int) band.getMembers().stream()
                                        .filter(member -> member.getName().contains(query))
                                        .count();
                                String bandNameWithCount = band.getName() + " [" + memberCount + "]";
                                return new Band(bandNameWithCount, band.getMembers());
                            })
                            .collect(Collectors.toSet());
                    return new Event(event.getId(), event.getTitle(), event.getImgUrl(), filteredBands,
                            event.getNbStars(), event.getComment());
                })
                .filter(event -> !event.getBands().isEmpty())
                .collect(Collectors.toList());
    }

    public Optional<Event> findById(Long id) {

        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event event) {

        if(!eventRepository.existsById(id)){
            throw new EntityNotFoundException("You can't update event with an unexisting id :" + id);
        }
        return save(event);
    }
    public Event save(Event event){
        return eventRepository.save(event);
    }
}
