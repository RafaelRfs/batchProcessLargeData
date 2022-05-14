package com.site.rfs.batchprocesslargefile.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.rfs.batchprocesslargefile.domain.Client;
import com.site.rfs.batchprocesslargefile.domain.PayloadJson;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ClientItemWriter implements ItemWriter<Client> {
    @Override
    public void write(List<? extends Client> clients) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setClients(clients);
        String payloadJsonData = objectMapper.writeValueAsString(payloadJson);
        String filename = "/process/data-result/jsonPayloadResult-"+ UUID.randomUUID().toString() +".json";
        File myObj = new File(filename);
        validateFile(myObj);
        processDataFile(payloadJsonData, filename);
    }

    private void processDataFile(String payloadJsonData, String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(payloadJsonData);
            myWriter.close();
            System.out.println("Arquivos gravados com sucesso: ");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro: ");
            e.printStackTrace();
        }
    }

    private void validateFile(File myObj) throws IOException {
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
        } else {
            System.out.println("File already exists.");
        }
    }
}
