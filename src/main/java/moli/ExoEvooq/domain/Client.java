package moli.ExoEvooq.domain;


import java.util.List;


public class Client {

    private String id;

    private String name;

    private List<Account> accountList;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }


    public Client(String id, String name, List<Account> accountList) {
        this.id = id;
        this.name = name;
        this.accountList = accountList;
    }

    public Client(String name) {
        this.name = name;
    }


    public void addAccount(Account account) {
        this.accountList.add(account);
    }
}
