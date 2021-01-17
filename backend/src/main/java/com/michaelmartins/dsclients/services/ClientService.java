package com.michaelmartins.dsclients.services;

import com.michaelmartins.dsclients.domain.entities.Client;
import com.michaelmartins.dsclients.dto.ClientDTO;
import com.michaelmartins.dsclients.exceptions.ResourceEntityNotFoundException;
import com.michaelmartins.dsclients.repositories.ClientRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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

    @Transactional
    public ClientDTO create(ClientDTO dto) {
        Client client = Client.copyDtoToEntity(dto, new Client());
        return new ClientDTO(repository.save(client));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client client = repository.getOne(id);
            Client.copyDtoToEntity(dto, client);
            return new ClientDTO(repository.save(client));
        } catch (EntityNotFoundException e) {
            throw new ResourceEntityNotFoundException(format("Entity de id '%s' não existe.", id));
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ResourceEntityNotFoundException(format("Entity de id '%s' não existe.", id));
        }
    }
}
