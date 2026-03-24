package org.example;


import org.example.dao.*;
import org.example.servise.*;

public class Main {
    public static void main(String[] args) {
        Database.getInstance();

        ClientDaoService dao = new ClientDaoServiceImpl();
        ClientService service = new ClientServiceImpl(dao);
        long id = service.create("Маруся");
        System.out.println("Created " + id + " - " + service.getById(id));
        service.setName(id, "Маруся2");
        System.out.println("Updated " + id + " - " + service.getById(id));
        service.listAll().forEach(c ->
                System.out.println(c.getId() + " " + c.getName())
        );
        service.deleteById(id);
        System.out.println("Deleted " + id);
        service.listAll().forEach(c ->
                System.out.println(c.getId() + " " + c.getName())
        );
    }
}