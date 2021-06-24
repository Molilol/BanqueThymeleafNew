package moli.ExoEvooq.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moli.ExoEvooq.domain.Client;
import moli.ExoEvooq.domain.ClientRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileClientRepository implements ClientRepository {

    @Override
    //TODO
    public Client findById(Long id) {


        return null;
    }

    @Override
    //TODO
    public void save(Client clientToSave) throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        String json = gson.toJson(clientToSave);
        String url = "C:\\Users\\Moli\\Desktop\\PROG\\ExoStageEvooq\\ExoEvooq\\src\\main\\resources\\saveClient\\" + clientToSave.getName() + ".json";
        BufferedWriter writer = new BufferedWriter(new FileWriter(url));
        writer.write(json);
        writer.close();

    }

}
