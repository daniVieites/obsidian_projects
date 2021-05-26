package com.example.prueba.service;

import com.example.prueba.model.NodeRoot;
import com.example.prueba.repository.NodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class NodeServiceTest {

    @Autowired private WebTestClient client;
    @Autowired private NodeService service;
    @MockBean private NodeRepository repository;

    @Test
    void testFindAll() {
        List<NodeRoot> nodes = Arrays.asList(new NodeRoot("node1"), new NodeRoot("node2"));
        when(repository.findAll()).thenReturn(Flux.fromIterable(nodes));

        var response = service.findAll().collectList().block();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("node1", response.get(0).getNombre());
        assertEquals("node2", response.get(1).getNombre());

        verify(repository).findAll();
    }
}