package com.michaelmartins.dsclients.services;

import com.michaelmartins.dsclients.dto.ClientDTO;
import com.michaelmartins.dsclients.exceptions.ResourceEntityNotFoundException;
import com.michaelmartins.dsclients.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        return repository.findById(id)
                .map(ClientDTO::new)
                .orElseThrow(() -> new ResourceEntityNotFoundException(format("Entity de id '%s' não existe.", id)));
    }
}
