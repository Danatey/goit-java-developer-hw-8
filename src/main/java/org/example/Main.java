package org.example;


public class Main {
    public static void main(String[] args) {
        Database.getInstance();

        ClientService clientService = new ClientService();
        long id = clientService.create("Маруся");
        System.out.println("Created " + id + " - " + clientService.getById(id));
        clientService.setName(id, "Маруся2");
        System.out.println("Updated " + id + " - " + clientService.getById(id));
        clientService.listAll().forEach(c ->
                System.out.println(c.getId() + " " + c.getName())
        );
        clientService.deleteById(id);
        System.out.println("Deleted " + id);
        clientService.listAll().forEach(c ->
                System.out.println(c.getId() + " " + c.getName())
        );
    }
}