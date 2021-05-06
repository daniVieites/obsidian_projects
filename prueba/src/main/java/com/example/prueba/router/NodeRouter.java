package com.example.prueba.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.example.prueba.handler.NodeHandler;

@Configuration
public class NodeRouter {
	
	@Bean
	public RouterFunction<ServerResponse> routes(NodeHandler handler){
		return route()
				.GET("/nodes", handler::findAll)
				.POST("/insert", handler::insert)
				.build();
	};
}
