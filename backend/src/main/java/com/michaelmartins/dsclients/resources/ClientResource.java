package com.michaelmartins.dsclients.resources;

import com.michaelmartins.dsclients.dto.ClientDTO;
import com.michaelmartins.dsclients.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    private final ClientService service;

    public ClientResource(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllPaged(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(name = "linesPerPage", defaultValue = "10") Integer linesPerPage,
                                                        @RequestParam(name = "direction", defaultValue = "ASC") String direction,
                                                        @RequestParam(name = "orderBy", defaultValue = "name") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(service.findAllPaged(pageRequest));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
