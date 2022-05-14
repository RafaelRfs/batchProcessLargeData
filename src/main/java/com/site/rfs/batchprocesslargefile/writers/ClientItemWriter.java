package com.site.rfs.batchprocesslargefile.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.rfs.batchprocesslargefile.domain.Client;
import com.site.rfs.batchprocesslargefile.domain.PayloadJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ClientItemWriter implements ItemWriter<Client> {

    @Override
    public void write(List<? extends Client> clients) throws Exception {
        log.info("Starting to write {} files a new file at: {}", clients.size(), Calendar.getInstance().getTime());
        ObjectMapper objectMapper = new ObjectMapper();
        PayloadJson payloadJson = new PayloadJson();
        payloadJson.setClients(clients);
        String payloadJsonData = objectMapper.writeValueAsString(payloadJson);
        String filename = "/process/data-result/jsonPayloadResult-"+ UUID.randomUUID().toString() +".json";
        File myObj = new File(filename);
        validateFile(myObj);
        processDataFile(payloadJsonData, filename);
        log.info("Ending saving {} files process at {} ",clients.size(), Calendar.getInstance().getTime());
    }

    private void processDataFile(String payloadJsonData, String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(payloadJsonData);
            myWriter.close();
            log.info("File {} saved with Sucess... ",filename);
        } catch (IOException e) {
            log.error("Error writing the file: {}", e.getMessage());
        }
    }

    private void validateFile(File myObj) throws IOException {
        if (myObj.createNewFile()) {
            log.info("File created: " + myObj.getName());
        } else {
            log.info("File {} already exists.",myObj.getName());
        }
    }
}
