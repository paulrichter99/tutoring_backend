package com.paulrichter.tutoring;

import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.repository.CalendarDateRepository;
import com.paulrichter.tutoring.repository.CalendarEventRepository;
import com.paulrichter.tutoring.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TutoringApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CalendarEventRepository eventRepository;

	@Autowired
	CalendarDateRepository calendarDateRepository;

	// TODO: make separate tests for separate things
	@Test
	public void testCreateUsersAndEvents() {
		// create some calendarDates
		LocalDateTime localDateTime1 = LocalDateTime.of(2023, Month.OCTOBER, 31, 10, 0);
		LocalDateTime localDateTime2 = LocalDateTime.of(2023, Month.OCTOBER, 10, 15, 0);
		LocalDateTime localDateTime3 = LocalDateTime.of(2023, Month.DECEMBER, 24, 18, 0);
		LocalDateTime localDateTime4 = LocalDateTime.of(2024, Month.OCTOBER, 15, 12, 30);

		CalendarDate calendarDate1 = calendarDateRepository.findById(1L).orElseGet(() -> new CalendarDate(localDateTime1));
		CalendarDate calendarDate2 = calendarDateRepository.findById(2L).orElseGet(() -> new CalendarDate(localDateTime2));
		CalendarDate calendarDate3 = calendarDateRepository.findById(3L).orElseGet(() -> new CalendarDate(localDateTime3));
		CalendarDate calendarDate4 = calendarDateRepository.findById(4L).orElseGet(() -> new CalendarDate(localDateTime4));

		// Check if events already exist, or create them if they don't
		CalendarEvent event1 = eventRepository.findByEventName("Event 1").orElseGet(() -> new CalendarEvent("Event 1", 90, List.of(calendarDate1)));
		CalendarEvent event2 = eventRepository.findByEventName("Event 2").orElseGet(() -> new CalendarEvent("Event 2", 120,  List.of(calendarDate2)));
		CalendarEvent event3 = eventRepository.findByEventName("Event 3").orElseGet(() -> new CalendarEvent("Event 3", 60, List.of(calendarDate3, calendarDate4)));

		// Check if users already exist, or create them if they don't
		User user1 = userRepository.findByUsername("user1").orElseGet(() -> new User("user1", "password1"));
		User user2 = userRepository.findByUsername("user2").orElseGet(() -> new User("user2", "password2"));
		User user3 = userRepository.findByUsername("user3").orElseGet(() -> new User("user3", "password3"));


		// Associate users with events
		event1.setEventUsers(Arrays.asList(user1, user2));
		event2.setEventUsers(Arrays.asList(user2, user3));
		event3.setEventUsers(Arrays.asList(user1, user3));

		// Add events to the users
		user1.setCalendarEvents(Arrays.asList(event1, event3));
		user2.setCalendarEvents(Arrays.asList(event1, event2));
		user3.setCalendarEvents(Arrays.asList(event2, event3));

		calendarDate1.setCalendarEvent(event1);
		calendarDate2.setCalendarEvent(event2);
		calendarDate3.setCalendarEvent(event3);
		calendarDate4.setCalendarEvent(event3);

		eventRepository.save(event1);
		eventRepository.save(event2);
		eventRepository.save(event3);
		// eventRepository.saveAll(Arrays.asList(event1, event2, event3));
		/*
		calendarDateRepository.save(calendarDate1);
		calendarDateRepository.save(calendarDate2);
		calendarDateRepository.save(calendarDate3);
		calendarDateRepository.save(calendarDate4);
		*/

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		// userRepository.saveAll(Arrays.asList(user1, user2, user3));


		// Retrieve the users and events from the database and assert the associations
		User persistedUser1 = userRepository.findByUsername("user1").orElse(null);
		User persistedUser2 = userRepository.findByUsername("user2").orElse(null);
		User persistedUser3 = userRepository.findByUsername("user3").orElse(null);

		assertNotNull(persistedUser1);
		assertNotNull(persistedUser2);
		assertNotNull(persistedUser3);

		assertEquals(2, persistedUser1.getCalendarEvents().size());
		assertEquals(2, persistedUser2.getCalendarEvents().size());
		assertEquals(2, persistedUser3.getCalendarEvents().size());

		CalendarEvent persistedEvent1 = eventRepository.findByEventName("Event 1").orElse(null);
		CalendarEvent persistedEvent2 = eventRepository.findByEventName("Event 2").orElse(null);
		CalendarEvent persistedEvent3 = eventRepository.findByEventName("Event 3").orElse(null);

		assertNotNull(persistedEvent1);
		assertNotNull(persistedEvent2);
		assertNotNull(persistedEvent3);

		assertEquals(2, persistedEvent1.getEventUsers().size());
		assertEquals(2, persistedEvent2.getEventUsers().size());
		assertEquals(2, persistedEvent3.getEventUsers().size());
	}
}
