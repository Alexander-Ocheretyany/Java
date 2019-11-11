package com.ocheretyany.alexander.REST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ocheretyany.alexander.REST.exception.NotFoundException;
import com.ocheretyany.alexander.REST.model.Record;
import com.ocheretyany.alexander.REST.repository.ConnectionRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class Controller {
	
	@Autowired
	ConnectionRepository connectionRepository;

    /**
     * Gets all records from "connections" table
     * @return All records found in the table
     */
	@GetMapping("/connection")
	public List<Record> getAllNotes() {
	    return connectionRepository.findAll();
	}

    /**
     * Creates a record in "connections" table
     * @param record - a valid object of type Record to be added
     * @return added record
     */
	@PostMapping("/connection")
	public Record createRecord(@Valid @RequestBody Record record) {
		return connectionRepository.save(record);
	}

    /**
     * Gets a record from "connections" table by its ID
     * @param recordId - the ID of a record to be altered
     * @return record found
     * @throws NotFoundException
     */
	@GetMapping("/connection/{id}")
	public Record getNoteById(@PathVariable(value = "id") Long recordId) throws NotFoundException {
		return connectionRepository.findById(recordId).orElseThrow(() -> new NotFoundException(recordId));
	}
	
    /**
     * Alters a record from "connections" table
     * @param recordId - the ID of a record to be altered
     * @param recordDetails - a valid Record object
     * @return altered record
     * @throws NotFoundException
     */
    @PutMapping("/connection/{id}")
    public Record updateNote(@PathVariable(value = "id") Long recordId,
                           @Valid @RequestBody Record recordDetails) throws NotFoundException {
    	Record record = connectionRepository.findById(recordId).orElseThrow(() -> new NotFoundException(recordId));

    	record.setName(recordDetails.getName());
        record.setHostname(recordDetails.getHostname());
        record.setPort(recordDetails.getPort());
        record.setUsername(recordDetails.getUsername());
        record.setPassword(recordDetails.getPassword());

        Record updatedRecord = connectionRepository.save(record);

        return updatedRecord;
    }
	
    /**
     * Deletes a record from "connections" table
     * @param recordId - the ID of a record to be deleted
     * @return HTTP status
     * @throws NotFoundException
     */
	 @DeleteMapping("/connection/{id}")
	 public ResponseEntity<?> deleteRecord(@PathVariable(value = "id") Long recordId) throws NotFoundException {
	        Record record = connectionRepository.findById(recordId)
	        		.orElseThrow(() -> new NotFoundException(recordId));
	        connectionRepository.delete(record);
	        return ResponseEntity.ok().build();
	 }
}
