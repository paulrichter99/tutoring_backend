package com.paulrichter.tutoring;

import com.paulrichter.tutoring.Enum.ERole;
import com.paulrichter.tutoring.controller.rest.UserController;
import com.paulrichter.tutoring.model.CalendarDate;
import com.paulrichter.tutoring.model.CalendarEvent;
import com.paulrichter.tutoring.model.Role;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.payload.request.SignupRequest;
import com.paulrichter.tutoring.repository.CalendarDateRepository;
import com.paulrichter.tutoring.repository.CalendarEventRepository;
import com.paulrichter.tutoring.repository.RoleRepository;
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
	private RoleRepository roleRepository;

	@Autowired
	CalendarDateRepository calendarDateRepository;

	@Autowired
	UserController userController;

	@Test
	public void testCreateRole(){
		Role role_user = roleRepository.findByName(ERole.ROLE_USER).orElse(new Role(ERole.ROLE_USER));
		Role role_moderator = roleRepository.findByName(ERole.ROLE_MODERATOR).orElse(new Role(ERole.ROLE_MODERATOR));
		Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(new Role(ERole.ROLE_ADMIN));

		roleRepository.save(role_user);
		roleRepository.save(role_moderator);
		roleRepository.save(role_admin);
	}

	// TODO: make separate tests for separate things
	@Test
	public void testCreateUsersAndEvents() {
		// create some calendarDates
		LocalDateTime localDateTime1 = LocalDateTime.of(2023, Month.OCTOBER, 31, 10, 0);
		LocalDateTime localDateTime2 = LocalDateTime.of(2023, Month.OCTOBER, 10, 15, 0);
		LocalDateTime localDateTime3 = LocalDateTime.of(2023, Month.DECEMBER, 24, 18, 0);
		LocalDateTime localDateTime4 = LocalDateTime.of(2024, Month.OCTOBER, 15, 12, 30);
		LocalDateTime localDateTime5 = LocalDateTime.of(2023, Month.OCTOBER, 17, 14, 30);

		CalendarDate calendarDate1 = calendarDateRepository.findById(1L).orElseGet(() -> new CalendarDate(localDateTime1));
		CalendarDate calendarDate2 = calendarDateRepository.findById(2L).orElseGet(() -> new CalendarDate(localDateTime2));
		CalendarDate calendarDate3 = calendarDateRepository.findById(3L).orElseGet(() -> new CalendarDate(localDateTime3));
		CalendarDate calendarDate4 = calendarDateRepository.findById(4L).orElseGet(() -> new CalendarDate(localDateTime4));
		CalendarDate calendarDate5 = calendarDateRepository.findById(5L).orElseGet(() -> new CalendarDate(localDateTime5));

		// Check if events already exist, or create them if they don't
		CalendarEvent event1 = eventRepository.findByEventName("Event 1").orElseGet(()
				-> new CalendarEvent("Event 1", 90, List.of(calendarDate1)));
		CalendarEvent event2 = eventRepository.findByEventName("Event 2").orElseGet(()
				-> new CalendarEvent("Event 2", 120,  List.of(calendarDate2)));
		CalendarEvent event3 = eventRepository.findByEventName("Event 3").orElseGet(()
				-> new CalendarEvent("Event 3", 60, List.of(calendarDate3, calendarDate4)));
		CalendarEvent event4 = eventRepository.findByEventName("Nachhilfe mit Lionel").orElseGet(()
				-> new CalendarEvent("Nachhilfe mit Lionel", 90, List.of(calendarDate5)));

		// Check if users already exist, or create them if they don't
		userRepository.findByUsername("user1").orElseGet(() -> {
			userController.registerUser(new SignupRequest("user1", "password1"));
			return userRepository.findByUsername("user1").orElse(null);
		});
		userRepository.findByUsername("user2").orElseGet(() -> {
			userController.registerUser(new SignupRequest("user2", "password2"));
			return userRepository.findByUsername("user2").orElse(null);
		});
		userRepository.findByUsername("user3").orElseGet(() -> {
			userController.registerUser(new SignupRequest("user3", "password3"));
			return userRepository.findByUsername("user3").orElse(null);
		});
		userRepository.findByUsername("paulrichter99").orElseGet(() -> {
			userController.registerUser(new SignupRequest("paulrichter99", "test1234"));
			return userRepository.findByUsername("paulrichter99").orElse(null);
		});

		// Retrieve the users and events from the database and assert the associations
		User persistedUser1 = userRepository.findByUsername("user1").orElse(null);
		User persistedUser2 = userRepository.findByUsername("user2").orElse(null);
		User persistedUser3 = userRepository.findByUsername("user3").orElse(null);
		User persistedUser4 = userRepository.findByUsername("paulrichter99").orElse(null);

		assertNotNull(persistedUser1);
		assertNotNull(persistedUser2);
		assertNotNull(persistedUser3);
		assertNotNull(persistedUser4);

		// Associate users with events
		event1.setEventUsers(Arrays.asList(persistedUser1, persistedUser2, persistedUser4));
		event2.setEventUsers(Arrays.asList(persistedUser2, persistedUser3, persistedUser4));
		event3.setEventUsers(Arrays.asList(persistedUser1, persistedUser3, persistedUser4));
		event4.setEventUsers(List.of(persistedUser4));


		// Add events to the users
		persistedUser1.setCalendarEvents(Arrays.asList(event1, event3));
		persistedUser2.setCalendarEvents(Arrays.asList(event1, event2));
		persistedUser3.setCalendarEvents(Arrays.asList(event2, event3));
		persistedUser4.setCalendarEvents(Arrays.asList(event1, event2, event3, event4));


		calendarDate1.setCalendarEvent(event1);
		calendarDate2.setCalendarEvent(event2);
		calendarDate3.setCalendarEvent(event3);
		calendarDate4.setCalendarEvent(event3);
		calendarDate5.setCalendarEvent(event4);

		eventRepository.save(event1);
		eventRepository.save(event2);
		eventRepository.save(event3);
		eventRepository.save(event4);
		// eventRepository.saveAll(Arrays.asList(event1, event2, event3));
		/*
		calendarDateRepository.save(calendarDate1);
		calendarDateRepository.save(calendarDate2);
		calendarDateRepository.save(calendarDate3);
		calendarDateRepository.save(calendarDate4);


		userRepository.save(persistedUser1);
		userRepository.save(persistedUser2);
		userRepository.save(persistedUser3);
		userRepository.save(persistedUser4);
		*/
		// userRepository.saveAll(Arrays.asList(user1, user2, user3));

		assertEquals(2, persistedUser1.getCalendarEvents().size());
		assertEquals(2, persistedUser2.getCalendarEvents().size());
		assertEquals(2, persistedUser3.getCalendarEvents().size());
		assertEquals(4, persistedUser4.getCalendarEvents().size());

		CalendarEvent persistedEvent1 = eventRepository.findByEventName("Event 1").orElse(null);
		CalendarEvent persistedEvent2 = eventRepository.findByEventName("Event 2").orElse(null);
		CalendarEvent persistedEvent3 = eventRepository.findByEventName("Event 3").orElse(null);
		CalendarEvent persistedEvent4 = eventRepository.findByEventName("Nachhilfe mit Lionel").orElse(null);

		assertNotNull(persistedEvent1);
		assertNotNull(persistedEvent2);
		assertNotNull(persistedEvent3);
		assertNotNull(persistedEvent4);

		assertEquals(3, persistedEvent1.getEventUsers().size());
		assertEquals(3, persistedEvent2.getEventUsers().size());
		assertEquals(3, persistedEvent3.getEventUsers().size());
		assertEquals(1, persistedEvent4.getEventUsers().size());
	}
}
