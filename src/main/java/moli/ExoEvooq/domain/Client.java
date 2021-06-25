package moli.ExoEvooq.domain;


import java.time.LocalDateTime;
import java.util.List;


public class Client {

    private String id;

    private String name;

    private List<Account> accountList;

    private LocalDateTime date;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Client(String id, String name, List<Account> accountList, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.accountList = accountList;
        this.date = date;
    }

    public Client(String name) {
        this.name = name;
    }


    public void addAccount(Account account) {
        this.accountList.add(account);
    }
}
