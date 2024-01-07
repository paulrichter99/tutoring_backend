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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		Role role_tutor = roleRepository.findByName(ERole.ROLE_TUTOR).orElse(new Role(ERole.ROLE_TUTOR));
		Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(new Role(ERole.ROLE_ADMIN));

		roleRepository.save(role_user);
		roleRepository.save(role_moderator);
		roleRepository.save(role_tutor);
		roleRepository.save(role_admin);
	}

	@Test
	public void testCreateAdminAccount(){
		userRepository.findByUsername("paulrichter99").orElseGet(() -> {
			userController.registerUser(new SignupRequest("paulrichter99", "test1234"));
			return userRepository.findByUsername("paulrichter99").orElse(null);
		});

		User persistedAdminUser = userRepository.findByUsername("paulrichter99").orElse(null);
		assertNotNull(persistedAdminUser);

		Set<Role> rolesForAdmin = new HashSet<>();
		Role role_user = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
		assertNotNull(role_user);
		rolesForAdmin.add(role_user);

		Role role_moderator = roleRepository.findByName(ERole.ROLE_MODERATOR).orElse(null);
		assertNotNull(role_moderator);
		rolesForAdmin.add(role_moderator);

		Role role_tutor = roleRepository.findByName(ERole.ROLE_TUTOR).orElse(null);
		assertNotNull(role_tutor);
		rolesForAdmin.add(role_tutor);

		Role role_admin = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
		assertNotNull(role_admin);
		rolesForAdmin.add(role_admin);

		persistedAdminUser.setRoles(rolesForAdmin);

		userRepository.save(persistedAdminUser);
	}

	// TODO: make separate tests for separate things
	// @Test
	public void testCreateUsersAndEvents() {
		// create some calendarDates
		ZonedDateTime localDateTime1 = ZonedDateTime.of(LocalDateTime.of(2023, Month.OCTOBER, 31, 10, 0), ZoneId.of("Europe/Berlin"));
		ZonedDateTime localDateTime2 = ZonedDateTime.of(LocalDateTime.of(2023, Month.OCTOBER, 10, 15, 0), ZoneId.of("Europe/Berlin"));
		ZonedDateTime localDateTime3 = ZonedDateTime.of(LocalDateTime.of(2023, Month.DECEMBER, 24, 18, 0), ZoneId.of("Europe/Berlin"));
		ZonedDateTime localDateTime4 = ZonedDateTime.of(LocalDateTime.of(2024, Month.OCTOBER, 15, 12, 30), ZoneId.of("Europe/Berlin"));
		ZonedDateTime localDateTime5 = ZonedDateTime.of(LocalDateTime.of(2023, Month.OCTOBER, 17, 14, 30), ZoneId.of("Europe/Berlin"));

		CalendarDate calendarDate1 = calendarDateRepository.findById(1L).orElseGet(() -> new CalendarDate(localDateTime1));
		CalendarDate calendarDate2 = calendarDateRepository.findById(2L).orElseGet(() -> new CalendarDate(localDateTime2));
		CalendarDate calendarDate3 = calendarDateRepository.findById(3L).orElseGet(() -> new CalendarDate(localDateTime3));
		CalendarDate calendarDate4 = calendarDateRepository.findById(4L).orElseGet(() -> new CalendarDate(localDateTime4));
		CalendarDate calendarDate5 = calendarDateRepository.findById(5L).orElseGet(() -> new CalendarDate(localDateTime5));

		// Check if events already exist, or create them if they don't
		CalendarEvent event1 = eventRepository.findByEventName("Event 1").orElseGet(()
				-> new CalendarEvent("Event 1", 90, calendarDate1));
		CalendarEvent event2 = eventRepository.findByEventName("Event 2").orElseGet(()
				-> new CalendarEvent("Event 2", 120,  calendarDate2));
		CalendarEvent event3 = eventRepository.findByEventName("Event 3").orElseGet(()
				-> new CalendarEvent("Event 3", 60, calendarDate3));
		CalendarEvent event4 = eventRepository.findByEventName("Nachhilfe mit Lionel").orElseGet(()
				-> new CalendarEvent("Nachhilfe mit Lionel", 90, calendarDate5));

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

		calendarDateRepository.save(calendarDate1);
		calendarDateRepository.save(calendarDate2);
		calendarDateRepository.save(calendarDate3);
		calendarDateRepository.save(calendarDate4);


		userRepository.save(persistedUser1);
		userRepository.save(persistedUser2);
		userRepository.save(persistedUser3);
		userRepository.save(persistedUser4);

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
