package com.algaworks.algafdood;

import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.MILLIS;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyFirstController {
	private long counter  = 0;
	private LocalDateTime lastAcess;
	@ResponseBody
	@GetMapping("/hello")
	public String hello() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		if( this.lastAcess == null) {
			this.lastAcess = currentDateTime;
		}
		long deltaTime = MILLIS.between(this.lastAcess, currentDateTime);
		this.lastAcess = currentDateTime;
		
		String timeAgo = (deltaTime < 1000) ? deltaTime + " ms" : ( deltaTime / 1000 ) + " s"; 
		
		this.counter++;
		
		
		return "<h1>First controller testing.</h1><h2>Access:["+counter+"] last access " + timeAgo + " ago</h2>";
	}

}
