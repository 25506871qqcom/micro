package com.example.demodocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
@RestController
public class DemodockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemodockerApplication.class, args);
	}


	@RequestMapping("index")
	public Object index(){
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("100","success");
		return hashMap ;
	}
}

