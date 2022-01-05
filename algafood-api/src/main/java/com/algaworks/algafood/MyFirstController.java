package com.algaworks.algafood;

import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.MILLIS;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
public class MyFirstController {
	
	private AtivacaoClienteService ativacaoCleinteService;
	
	private long counter  = 0;
	private LocalDateTime lastAcess;
	
	public MyFirstController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoCleinteService = ativacaoClienteService;
		
		System.out.println("MyFirstController " + ativacaoClienteService );
	}
	
	@ResponseBody
	@GetMapping("/hello")
	public String hello() {
	
		Cliente joao = new Cliente("João", "joao@xyz.com", "21999998888");
		
		ativacaoCleinteService.ativar(joao);		
		
		String timeLapsed = updateLastAccess();
				
		return "<h1>First controller testing.</h1><h2>Access:["+counter+"] last access " + timeLapsed + " ago</h2>";
	}

	private String updateLastAccess() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		if( this.lastAcess == null) {
			this.lastAcess = currentDateTime;
		}
		
		long deltaTime = MILLIS.between(this.lastAcess, currentDateTime);
		
		this.lastAcess = currentDateTime;
		
		String timeLapsed = (deltaTime < 1000) ? deltaTime + " ms" : ( deltaTime / 1000 ) + " s"; 
		
		this.counter++;
		
		return timeLapsed;
	}

}
