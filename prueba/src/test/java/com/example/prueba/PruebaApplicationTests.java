package com.example.prueba;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.prueba.model.NodeDesc;
import com.example.prueba.model.NodeRoot;

import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PruebaApplicationTests {

	@Autowired
	private WebTestClient client;
	
	@Test
	public void listNodes() {
		client.get()
		.uri("/nodes")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(NodeRoot.class);
	}
	
//	@Test
//	public void createDescNode() {
//		NodeRoot nodeDesc = new NodeDesc(new ObjectId(), "desc", "desc description");
//		
//		client.post()
//		.uri("/insert")
//		.contentType(MediaType.APPLICATION_JSON)
//		.accept(MediaType.APPLICATION_JSON)
//		.body(Mono.just(nodeDesc), NodeRoot.class)
//		.exchange()
//		.expectStatus().isCreated()
//		.expectBody(NodeDesc.class)
//		.consumeWith(response -> {
//			NodeDesc node = response.getResponseBody();
//			assertThat(node.getNombre()).isEqualTo("desc");
//			assertThat(node.getDescripcion()).isEqualTo("desc description");
//		});
//	}
//	
//	@Test
//	public void createRootNode() {
//		NodeRoot nodeRoot = new NodeRoot(new ObjectId(), "root");
//		
//		client.post()
//		.uri("/insert")
//		.contentType(MediaType.APPLICATION_JSON)
//		.accept(MediaType.APPLICATION_JSON)
//		.body(Mono.just(nodeRoot), NodeRoot.class)
//		.exchange()
//		.expectStatus().isCreated()
//		.expectBody(NodeRoot.class)
//		.consumeWith(response -> {
//			NodeRoot node = response.getResponseBody();
//			assertThat(node.getNombre()).isEqualTo("root");
//		});
//	}
}
