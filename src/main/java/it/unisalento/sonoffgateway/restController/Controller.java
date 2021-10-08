package it.unisalento.sonoffgateway.restController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping(value="changeStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> changeStatus(){
		try {
			//TODO: istruzioni al dispositivo sonoff
			return new ResponseEntity<Boolean>(HttpStatus.OK);
		}catch (Exception e) {
			throw e;
			}
		
	}
}
