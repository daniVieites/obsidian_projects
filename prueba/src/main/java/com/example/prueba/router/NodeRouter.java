package com.example.prueba.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import com.example.prueba.handler.NodeHandler;

@Configuration
public class NodeRouter {
	
	@Bean
	public RouterFunction<ServerResponse> route(){
		NodeHandler nodeHandler = new NodeHandler();
		return RouterFunctions
				.route(GET("/nodes"), nodeHandler::findAll)
				.andRoute(POST("/insert"), nodeHandler::insert);
	};
}
