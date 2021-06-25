package moli.ExoEvooq.vue;

import moli.ExoEvooq.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountDTO {

    String id;
    String devise;
    List<OperationDTO> operationList;
    Double total;
    LocalDateTime date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public List<OperationDTO> getOperationList() {
        if (operationList == null) {
            this.operationList = new ArrayList<>();
        }
        return operationList;
    }

    public void setOperationList(List<OperationDTO> operationList) {
        this.operationList = operationList;
    }

    public String getNbOperation() {
       return String.valueOf(operationList.size());
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AccountDTO() {
    }
}
