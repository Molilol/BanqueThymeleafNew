package moli.ExoEvooq.vue;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO {

    String id;
    ClientDTO clientDTO;
    String devise;
    List<OperationDTO> operationList;
    String total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public AccountDTO() {
    }
}
