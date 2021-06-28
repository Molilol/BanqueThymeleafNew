package moli.ExoEvooq.infrastructure;

import moli.ExoEvooq.domain.Account;
import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.infrastructure.persistance.AccountEntity;
import moli.ExoEvooq.infrastructure.persistance.ClientEntity;
import moli.ExoEvooq.infrastructure.persistance.OperationEntity;
import moli.ExoEvooq.service.AccountService;
import moli.ExoEvooq.service.ClientService;
import moli.ExoEvooq.service.OperationService;
import moli.ExoEvooq.vue.*;
import moli.ExoEvooq.wrapper.WrapperDTOtoEntity;
import moli.ExoEvooq.wrapper.WrapperDomainToDTO;
import moli.ExoEvooq.wrapper.WrapperEntityToDTO;
import moli.ExoEvooq.wrapper.WrapperEntityToDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
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
    @Autowired
    private OperationRepoHibernate operationRepoHibernate;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OperationService operationService;


    @RequestMapping({"/index", "/"})
    public ModelAndView index() {
        List<ClientEntity> clientEntityList = clientRepoHibernate.findAll();
        List<Client> clients = wrapperEntityToDomain.clientEntitySetToClientDomainList(clientEntityList);
        List<ClientDTO> clientDTOList = wrapperDomainToDTO.clientDomainListToClientDTOList(clients);
        String totalAllClients = clientService.syntheseTotalAllClients(clientDTOList);
        String nbAccountAllClients = clientService.nbAccountAllClients(clientDTOList);
        int nbClients = clientDTOList.size();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("clients", clientDTOList);
        modelAndView.addObject("nbClients", nbClients);
        modelAndView.addObject("nbAccountAllClients", nbAccountAllClients);
        modelAndView.addObject("totalAllClients", totalAllClients);
        return modelAndView;

    }

    @GetMapping(path = "/clients")
    public ModelAndView getClients() {
        List<ClientEntity> clientEntityList = clientRepoHibernate.findAll();
        List<Client> clientList = wrapperEntityToDomain.clientEntitySetToClientDomainList(clientEntityList);
        List<ClientDTO> clientDTOList = wrapperDomainToDTO.clientDomainListToClientDTOList(clientList);
        ModelAndView modelAndView = new ModelAndView("clients");
        modelAndView.addObject("clients", clientDTOList);
        return modelAndView;
    }

    @GetMapping(path = "clients/{userId}")
    public ModelAndView getClientPerId(@PathVariable String userId) {
        Optional<ClientEntity> clientEntityOp = clientRepoHibernate.findById(userId);
        ClientEntity clientEntity = clientEntityOp.get();
        Client client = wrapperEntityToDomain.clientEntityToDomain(clientEntity);
        ClientDTO clientDTO = wrapperDomainToDTO.clientDomainToClientDTO(client);
        ModelAndView modelAndView = new ModelAndView("choix");
        modelAndView.addObject("client", clientDTO);
        return modelAndView;

    }

    @GetMapping(path = "account/{accountId}/{page}")
    public ModelAndView getAccountById(@PathVariable String accountId, @PathVariable(value = "page") int page) {
        AccountEntity accountEntity = accountRepoHibernate.findById(accountId).get();
        Account account = clientService.accountEntityToAccountDomain(accountEntity);
        AccountDTO accountDTO = wrapperDomainToDTO.accountDomainToAccountDTO(account);
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        Page<OperationEntity> operationEntityPage = operationRepoHibernate.findByAccountId(accountId, PageRequest.of(page, 5, sort));
        List<OperationDTO> operationDTOList = wrapperEntityToDTO.operationEntityPageToOperationDTOList(operationEntityPage);
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("operationPage", operationDTOList);
        modelAndView.addObject("totalPage", operationEntityPage.getTotalPages());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("account", accountDTO);
        return modelAndView;

    }

    @GetMapping(path = "operation/{userId}")
    public ModelAndView getOperationPage(@PathVariable String userId) {
        ClientEntity clientEntity = clientRepoHibernate.findById(userId).get();
        Client client = wrapperEntityToDomain.clientEntityToDomain(clientEntity);
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
        accountService.createAccount(accountCreateDTO);
        return new ModelAndView("redirect:/clients/" + accountCreateDTO.getClientId());
    }

    @GetMapping(path = "addOperation/{accountId}")
    public ModelAndView addOperationGet(@PathVariable String accountId) {
        ModelAndView modelAndView = new ModelAndView("addOperation");
        modelAndView.addObject("accountId", accountId);
        return modelAndView;
    }

    @PostMapping(value = "/addOperation")
    public ModelAndView addOperation(@ModelAttribute OperationCreateDTO operationCreateDTO) {
        operationService.createOperation(operationCreateDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/account/" + operationCreateDTO.getAccountId() + "/0");
        return modelAndView;
    }

    @GetMapping(path = "account/supOperation/{accountId}/{operationId}/{currentPage}")
    public ModelAndView supOperation(@PathVariable String accountId, @PathVariable String operationId, @PathVariable String currentPage) {
        operationRepoHibernate.deleteById(operationId);
        ModelAndView modelAndView = new ModelAndView("redirect:/account/" + accountId + "/" + currentPage);
        return modelAndView;
    }

    @GetMapping(path = "/createClient")
    public ModelAndView createClient() {
        ModelAndView modelAndView = new ModelAndView("createClient");
        return modelAndView;
    }

    @PostMapping(value = "/creation")
    public ModelAndView postCreation(@ModelAttribute("userCreateDTO") UserCreateDTO userCreateDTO) {
        clientService.createUser(userCreateDTO);
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
