package com.rest.webservices.restexample.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	@Autowired
	private UserDaoService service;
	
	@GetMapping(path= "/users")
	public List<User> retreiveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping(path= "/users/{id}")
	public EntityModel<User> retreiveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
		
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linkToUsers = 
				linkTo(methodOn(this.getClass()).retreiveAllUsers());
		model.add(linkToUsers.withRel("all-users"));
		return model;
	}
	
	@PostMapping(path= "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		//create new URI 
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId()).toUri();
		//Status: created
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path= "/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException("id-"+id);
		}
	}
	
}
