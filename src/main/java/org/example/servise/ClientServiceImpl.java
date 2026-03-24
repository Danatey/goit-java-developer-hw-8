package org.example.servise;

import org.example.Client;
import org.example.dao.ClientDaoService;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientDaoService dao;

    private static final int MIN_NAME = 2;
    private static final int MAX_NAME = 100;

    public ClientServiceImpl(ClientDaoService dao) {
        this.dao = dao;
    }

    @Override
    public long create(String name) {
        validateName(name);
        return dao.create(name.trim());
    }

    @Override
    public String getById(long id) {
        validateId(id);

        String result = dao.getById(id);

        if (result == null) {
            throw new RuntimeException("Client not found id=" + id);
        }

        return result;
    }

    @Override
    public void setName(long id, String name) {
        validateId(id);
        validateName(name);

        dao.setName(id, name.trim());
    }

    @Override
    public void deleteById(long id) {
        validateId(id);
        dao.deleteById(id);
    }

    @Override
    public List<Client> listAll() {
        return dao.listAll();
    }

    private void validateName(String name) {
        if (name == null || name.trim().length() < MIN_NAME || name.trim().length() > MAX_NAME) {
            throw new RuntimeException("Invalid name");
        }
    }

    private void validateId(long id) {
        if (id <= 0) {
            throw new RuntimeException("Invalid id");
        }
    }
}
