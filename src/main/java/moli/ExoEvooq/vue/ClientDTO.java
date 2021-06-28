package moli.ExoEvooq.vue;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientDTO {

    String id;
    String name;
    List<AccountDTO> accountClient;
    LocalDateTime date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public Double totalAllAccount() {
        Double calculAllTotal = 0.0;
        for (AccountDTO accountDTO : accountClient) {
            calculAllTotal += accountDTO.getTotal();
        }
       return calculAllTotal;
    }

    public String totalAllAccountFormat(){
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(totalAllAccount());
    }

    public List<AccountDTO> getAccountClient() {
        if (accountClient == null) {
            this.accountClient = new ArrayList<>();
        }
        return accountClient;
    }

    public void setAccountClient(List<AccountDTO> accountClient) {
        this.accountClient = accountClient;
    }

    public String nbAccount() {
        return String.valueOf(accountClient.size());
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String dateFormat() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public int nbOperation (){
        int nbOperation = 0;
        for (AccountDTO accountDTO : this.accountClient) {
           nbOperation += accountDTO.getNbOperation();
        }
        return nbOperation;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ClientDTO(String id, String name, List<AccountDTO> accountClient, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.accountClient = accountClient;
        this.date = date;
    }

    public ClientDTO() {
    }

}
