package adeo.leroymerlin.cdp.repository;

import adeo.leroymerlin.cdp.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {

    // inutile après changer Repository par CrudRepository
    //void deleteById(Long eventId);

    List<Event> findAllBy();
}
