package de.fhaachen.ipdashboard.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fhaachen.ipdashboard.model.Notification;
import de.fhaachen.ipdashboard.repository.NotificationRepository;
import de.fhaachen.ipdashboard.database.Dashboard;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/notification") // This means URL's start with /demo (after Application path)
public class NotificationController {
	@Autowired // This means to get the bean called NotificationRepository
				// Which is auto-generated by Spring, we will use it to handle the data
	private NotificationRepository notificationRepository;

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Notification> getAllNotifications() {
		return notificationRepository.findAll();
	}

    @PostMapping(path = "/add")
	@ResponseBody
	public boolean addUserToNotification(@RequestBody Notification notification) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.addUserToNotification(notification.getUsername());
		dash.close();
		return result;
	}
	
	@DeleteMapping(path = "/delete")
	@ResponseBody
	public boolean deleteUserFromNotification(@RequestParam String username) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.deleteUserFromNotification(username);
		dash.close();
		return result;
	}
	

/*
	@PostMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addNotification(@RequestBody Notification notification) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		notificationRepository.save(notification);
		return "Saved";
	}

	@DeleteMapping(path = "/delete") // Map ONLY GET Requests
	public @ResponseBody String deleteNotification(@RequestParam String username) {
		if (notificationRepository.existsByUsername(username)) {
			notificationRepository.deleteByUsername(username);
			return "Notification deleted.";
		} else
			return "Notification not found...";
	}

	*/

}