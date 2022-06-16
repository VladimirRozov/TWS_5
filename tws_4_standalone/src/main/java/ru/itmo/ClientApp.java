/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.itmo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author rozov
 */
public class ClientApp {
 
    private static final String URL = "http://localhost:8080/rest/loyalty";
    public static void main(String[] args) {
        Client client = Client.create();

        // Консольный выбор CRUD метода
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose CRUD method (input CREATE, READ, UPDATE or DELETE), or input 'exit' for exit:");
        String chosenMethod;
        do {
            chosenMethod = scanner.nextLine();
            // проверим строку на наличие аргумента: если строка не является пустой и не состоит из пробелов, то
            // проверяем на наличие одной из возможных операций
            if (chosenMethod != null && !chosenMethod.trim().isEmpty()) {

                switch (chosenMethod) {
                    case ("CREATE"):
                        createLoyalty(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("READ"):
                        readLoyalty(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("UPDATE"):
                        updateLoyalty(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("DELETE"):
                        deleteLoyalty(client);
                        System.out.println("That's it! You can choose another CRUD method or input 'exit' for exit");
                        break;
                    case ("exit"):
                        System.out.println("Bye-Bye!");
                        break;
                    default:
                        System.out.println("You can input just CREATE, READ, UPDATE or DELETE!");
                        System.out.println("Try again or use 'exit' for exit.");
                        break;
                }
            }
        } while (!Objects.equals(chosenMethod, "exit"));

        scanner.close();

    }

    private static void updateLoyalty(Client client) {
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input rowID (integer): ");
        String rowIDString = scanner.nextLine();
        // Проверяем rowId на число
        int rowId = -1;
        if (rowIDString != null && !rowIDString.trim().isEmpty()) {
            try {
                rowId = Integer.parseInt(rowIDString.trim());
            } catch (NumberFormatException ex) {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Your request is incorrect! RowId is not an integer.");
            }
        }

        if (rowId != -1) {

            System.out.println("What fields you want update for this row? \n" +
                    "Choose fields from 'spbso', 'name', 'brigade', 'event', 'cash' and input it's below \n" +
                    " separated by comma without spaces");
            String updateRows = scanner.nextLine();

            // Преобразуем полученную строку в список аргументов
            String[] updateRowsList = updateRows.split(",", -1);

            Map<String, String> updateRowsMap = new HashMap<>();
            updateRowsMap.put("spbso", "");
            updateRowsMap.put("name", "");
            updateRowsMap.put("brigade", "");
            updateRowsMap.put("event", "");
            updateRowsMap.put("cash", "");

            /*
            Проходим по списку аргументов и определяем совпадение с названиями полей таблицы БД.
            Проверка на пустую строку или строку из пробелов не нужна, т.к. в подобных случаях
            мы сразу перейдем к операции по умолчанию (default) в switch-case. То же самое мы получим,
            если не было совпадений по шаблону.
            Если совпадение найдено, то мы сразу запрашиваем ввести соответствующее значение, проверяем его на пустую
            строку и добавляем в массив, который будет формировать значения в запросе.
            Если в итоге сформирован пустой массив (ни одно из значений не подошло), то мы выводим сообщение,
            что введен некорректный запрос. Целочисленные значения age и student_id дополнительно проверяем на число (int).
            */
            for (String row : updateRowsList) {
                switch (row) {
                    case "name":
                        System.out.println("Input new value for 'name' field:");
                        String name = scanner.nextLine();
                        if (name != null && !name.trim().isEmpty()) {
                            updateRowsMap.put("name", name);
                        } else {
                            System.out.println("Field 'name' is incorrect and will not be updated!");
                        }
                        break;
                    case "brigade":
                        System.out.println("Input new value for 'brigade' field:");
                        String brigade = scanner.nextLine();
                        if (brigade != null && !brigade.trim().isEmpty()) {
                            updateRowsMap.put("brigade", brigade);
                        } else {
                            System.out.println("Field 'brigade' is incorrect and will not be updated!");
                        }
                        break;

                    case "spbso":
                        System.out.println("Input new value for 'spbso' field (integer):");
                        String spbso = scanner.nextLine();
                        try {
                            Integer.parseInt(spbso.trim());
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                            spbso = "";
                        }
                        if (!spbso.trim().isEmpty()) {
                            updateRowsMap.put("spbso", spbso);
                        } else {
                            System.out.println("Field 'spbso' is not an integer and will not be updated!");
                        }
                        break;

                    case "cash":
                        System.out.println("Input new value for 'cash' field (integer):");
                        String cash = scanner.nextLine();
                        try {
                            Integer.parseInt(cash.trim());
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Field 'cash' is not an integer and will not be updated!");
                            cash = "";
                        }
                        if (!cash.trim().isEmpty()) {
                            updateRowsMap.put("cash", cash);
                        } else {
                            System.out.println("Field 'cash' is not an integer and will not be updated!");
                        }
                        break;

                    case "event":
                        System.out.println("Input new value for 'event' field:");
                        String event = scanner.nextLine();
                        if (event != null && !event.trim().isEmpty()) {
                            updateRowsMap.put("event", event);
                        } else {
                            System.out.println("Field 'event' is incorrect and will not be updated!");
                        }
                        break;

                    default:
                        System.out.println("Incorrect request. Try again!");
                        break;
                }
            }

            int i = 0;
            for(String val : updateRowsMap.values()){
                if (val != null && !val.trim().isEmpty()) {
                    i++;
                }
            }

            if (i != 0) {

                System.out.println("You input new values for row " + rowId + ":\n" + updateRowsMap);
                System.out.println("Do you really want to change this fields for row " + rowId + "? (y -> yes, other -> no)");
                String agree = scanner.nextLine();

                if (agree.equals("y")) {
                    String spbso = updateRowsMap.get("spbso");
                    String name = updateRowsMap.get("name");
                    String brigade = updateRowsMap.get("brigade");
                    String event = updateRowsMap.get("event");
                    String cash = updateRowsMap.get("cash");

                    WebResource webResource = client.resource(URL);
                    webResource = webResource.queryParam("rowId",
                            rowIDString).queryParam("spbso", spbso).queryParam("name",
                            name).queryParam("brigade", brigade).queryParam("event",
                            event).queryParam("cash", cash);
                    ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
                    if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                        throw new IllegalStateException("Request failed");
                    }
                    System.out.println(response.getStatus());

                } else {
                    System.out.println("You just cancel your request. Try another request or exit.");
                }

            } else {
                System.out.println("All arguments is empty. Row update can not be completed.");
            }
        }
    }

    private static void deleteLoyalty(Client client) {
        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input rowId (integer): ");
        String rowId = scanner.nextLine();

        try {
            Integer.parseInt(rowId.trim());

            WebResource webResource = client.resource(URL);
            webResource = webResource.queryParam("rowId", rowId);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
            if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                throw new IllegalStateException("Request failed");
            }
            System.out.println(response.getStatus());

        } catch (NumberFormatException ex) {
            System.out.println("Incorrect rowId value! Input just one integer.");
        }
    }

    private static void createLoyalty(Client client) {

        // Консольный ввод аргументов
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input spbso (integer): ");
        String spbso = scanner.nextLine();
        System.out.print("Input name: ");
        String name = scanner.nextLine();
        System.out.print("Input brigade: ");
        String brigade = scanner.nextLine();
        System.out.print("Input event: ");
        String event = scanner.nextLine();
        System.out.print("Input cash (integer): ");
        String cash = scanner.nextLine();

        // проверим ввод на наличие значений: строка не является пустой и не состоит из пробелов
        if ((name != null && !name.trim().isEmpty())  &&
                (brigade != null && !brigade.trim().isEmpty()) &&
                (event != null && !event.trim().isEmpty()) &&
                (spbso != null && !spbso.trim().isEmpty()) &&
                (cash != null && !cash.trim().isEmpty())) {
            try {
                Integer.parseInt(spbso.trim());
                Integer.parseInt(cash.trim());

                WebResource webResource = client.resource(URL);

                webResource = webResource.queryParam("spbso", spbso).queryParam("name",
                        name).queryParam("brigade", brigade).queryParam("event",
                        event).queryParam("cash", cash);

                ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
                if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
                    throw new IllegalStateException("Request failed");
                }
                System.out.println(response.getStatus());

            } catch (NumberFormatException ex) {
                System.out.println("Incorrect age or studentId value!");
            }
        }
        else {
            System.out.println("Your request is incorrect!");
        }
    }
    private static void readLoyalty(Client client){
    for (Loyalty loyalty : findAll(client)) {
            System.out.println(loyalty);
        }
    }

    private static List<Loyalty> findAll(Client client) {
        WebResource webResource = client.resource(URL);

            webResource = webResource.queryParam(URL, URL);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Loyalty>> type = new GenericType<List<Loyalty>>() {};
        return response.getEntity(type);
    }
}
