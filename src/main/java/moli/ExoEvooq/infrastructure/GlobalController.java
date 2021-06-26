package moli.ExoEvooq.infrastructure;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.service.ClientService;
import moli.ExoEvooq.vue.*;
import moli.ExoEvooq.wrapper.WrapperDTOtoEntity;
import moli.ExoEvooq.wrapper.WrapperDomainToDTO;
import moli.ExoEvooq.wrapper.WrapperEntityToDTO;
import moli.ExoEvooq.wrapper.WrapperEntityToDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GlobalController {

    @Autowired
    private ClientRepoHibernate clientRepoHibernate;
    @Autowired
    private WrapperEntityToDTO wrapperEntityToDTO;
    @Autowired
    private WrapperDTOtoEntity wrapperDTOtoEntity;
    @Autowired
    private ClientService clientService;
    @Autowired
    private WrapperEntityToDomain wrapperEntityToDomain;
    @Autowired
    private WrapperDomainToDTO wrapperDomainToDTO;
    @Autowired
    private AccountRepoHibernate accountRepoHibernate;


  /*  @GetMapping("/accueil")
    public ModelAndView accueil() {
        return new ModelAndView("accueil");
    }
*/
  @GetMapping(path = "/index")
  public ModelAndView getAllClient() {
     /* List<ClientDTO> clientDTOList = new ArrayList<>();
      List<ClientEntity> clientEntityList = clientRepoHibernate.findAll();
      for (ClientEntity clientEntity : clientEntityList) {
          clientDTOList.add(wrapperEntityToDTO.clientEntityToClientDTO(clientEntity));
      }

      ModelAndView modelAndView = new ModelAndView("index");
      modelAndView.addObject("clients", clientDTOList);
      return modelAndView;
 */
      List<ClientEntity> clientEntityList = clientRepoHibernate.findAll();
      List<Client> clients = new ArrayList<>();
      for (ClientEntity clientEntity : clientEntityList){
          clients.add(wrapperEntityToDomain.ClientEntityToDomain(clientEntity));
      }
      List<ClientDTO> clientDTOList = new ArrayList<>();

      for (Client client : clients) {
          clientDTOList.add(wrapperDomainToDTO.clientDomainToClientDTO(client));
      }
      ModelAndView modelAndView = new ModelAndView("index");
      modelAndView.addObject("clients", clientDTOList);
      return modelAndView;

  }



    @GetMapping(path = "/clients")
    public ModelAndView getClients() {
        List<ClientDTO> clientDTOList = new ArrayList<>();
        List<ClientEntity> clientEntityList = clientRepoHibernate.findAll();
        for (ClientEntity clientEntity : clientEntityList) {
            ClientDTO clientDTO = wrapperEntityToDTO.clientEntityToClientDTO(clientEntity);
            clientDTOList.add(clientDTO);
        }
        ModelAndView modelAndView = new ModelAndView("clients");
        modelAndView.addObject("clients", clientDTOList);
        return modelAndView;
       // return clientDTOList;
    }

    @GetMapping(path = "clients/{userId}")
    public ModelAndView getClientPerId(@PathVariable String userId) {
        Optional<ClientEntity> clientEntityOp = clientRepoHibernate.findById(userId);
        ClientEntity clientEntity = clientEntityOp.get();
        Client client = wrapperEntityToDomain.ClientEntityToDomain(clientEntity);
        ClientDTO clientDTO = wrapperDomainToDTO.clientDomainToClientDTO(client);
        ModelAndView modelAndView = new ModelAndView("choix");
        modelAndView.addObject("client", clientDTO);
        return modelAndView;

    }

    @GetMapping(path = "account/{accountId}")
    public ModelAndView getAccountById(@PathVariable String accountId) {
        AccountEntity accountEntity = accountRepoHibernate.findById(accountId).get();
        Account account = clientService.accountEntityToAccountDomain(accountEntity);
        AccountDTO accountDTO = wrapperDomainToDTO.accountDomainToAccountDTO(account);
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("account", accountDTO);
        return modelAndView;

    }

    @GetMapping(path = "operation/{userId}")
    public ModelAndView getOperationPage(@PathVariable String userId) {
        Optional<ClientEntity> clientEntityOp = clientRepoHibernate.findById(userId);
        ClientEntity clientEntity = clientEntityOp.get();
        Client client = wrapperEntityToDomain.ClientEntityToDomain(clientEntity);
        ClientDTO clientDTO = wrapperDomainToDTO.clientDomainToClientDTO(client);
        ModelAndView modelAndView = new ModelAndView("operation");
        modelAndView.addObject("client", clientDTO);
        return modelAndView;
    }

    @GetMapping(path = "addAccount/{clientId}")
    public ModelAndView addAccount(@PathVariable String clientId) {
        ModelAndView modelAndView = new ModelAndView("addAccount");
        modelAndView.addObject("clientIdSend", clientId);
        return modelAndView;
    }

    @PostMapping(value = "/addAccount")
    public ModelAndView addAccountPost(@ModelAttribute AccountCreateDTO accountCreateDTO) {
        ClientEntity clientEntity = clientRepoHibernate.findById(accountCreateDTO.getClientId()).get();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setDevise("Euros");
        accountDTO.setDate(LocalDateTime.now());
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setDate(LocalDateTime.now());
        operationDTO.setOperationType("DEPOSER");
        operationDTO.setMontant(accountCreateDTO.getSomme());
        List <OperationDTO> operationDTOList = new ArrayList<>();
        operationDTOList.add(operationDTO);
        accountDTO.setOperationList(operationDTOList);
        accountRepoHibernate.save(wrapperDTOtoEntity.accountDTOtoAccountEntity(accountDTO, clientEntity));
        ModelAndView modelAndView = new ModelAndView("redirect:/clients/"+clientEntity.getId());


        return modelAndView;
    }

    @GetMapping(path = "addOperation/{accountId}")
    public ModelAndView addOperationGet(@PathVariable String accountId) {
        ModelAndView modelAndView = new ModelAndView("addOperation");
        modelAndView.addObject("accountId", accountId);
        return modelAndView;
    }

    @PostMapping(value = "/addOperation")
    public ModelAndView addOperation(@ModelAttribute OperationCreateDTO operationCreateDTO) {
        AccountEntity accountEntity = accountRepoHibernate.findById(operationCreateDTO.getAccountId()).get();
        ClientEntity clientEntity = clientRepoHibernate.findById(accountEntity.getClient().getId()).get();
        OperationDTO operationDTO = new OperationDTO();
       operationDTO.setMontant(operationCreateDTO.getMontant());
       operationDTO.setOperationType(operationCreateDTO.getTypeOperation());
       operationDTO.setDate(LocalDateTime.now());
        AccountDTO accountDTO = wrapperEntityToDTO.accountEntityToAccountDTO(accountEntity);
        accountDTO.getOperationList().add(operationDTO);
        accountRepoHibernate.save(wrapperDTOtoEntity.accountDTOtoAccountEntity(accountDTO, clientEntity));
        ModelAndView modelAndView = new ModelAndView("redirect:/account/"+operationCreateDTO.getAccountId());
        return modelAndView;
    }

    @GetMapping(path = "/createClient")
    public ModelAndView createClient() {
        ModelAndView modelAndView = new ModelAndView("createClient");
        return modelAndView;

    }

    @PostMapping(value = "/creation")
    public ModelAndView postCreation(@ModelAttribute("userCreateDTO") UserCreateDTO userCreateDTO) {
      ClientDTO clientDTO = new ClientDTO();
      clientDTO.setName(userCreateDTO.getName());
      clientDTO.setDate(LocalDateTime.now());
      AccountDTO accountDTO = new AccountDTO();
      accountDTO.setDevise("Euros");
      accountDTO.setDate(LocalDateTime.now());
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setOperationType("DEPOSER");
        operationDTO.setMontant(userCreateDTO.getMontant());
        operationDTO.setDate(LocalDateTime.now());
        List <OperationDTO> operationDTOList = new ArrayList<>();
        operationDTOList.add(operationDTO);
        accountDTO.setOperationList(operationDTOList);
        List <AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        clientDTO.setAccountClient(accountDTOList);

        clientService.addNewClient(wrapperDTOtoEntity.clientDTOtoClientEntity(clientDTO));


        return new ModelAndView("redirect:/index");

    }

/*

    @PostMapping(path = "/createClient")
    public void createClient(@RequestBody ClientDTO clientDTO) {
        ClientEntity clientEntity = wrapperDTOtoEntity.clientDTOtoClientEntity(clientDTO);
        clientService.addNewClient(clientEntity);
    }

    @PutMapping(path = "/updateClient")
    public void updateClient(@RequestBody ClientEntity clientEntity) {
        clientRepoHibernate.save(clientEntity);
    }

    @DeleteMapping(path = "/deleteClient/{name}")
    public void deleteClient(@PathVariable("name") String name) {
        ClientEntity clientEntity = clientRepoHibernate.findByName(name).get();
        clientRepoHibernate.delete(clientEntity);
    }
 * /




    */
}
