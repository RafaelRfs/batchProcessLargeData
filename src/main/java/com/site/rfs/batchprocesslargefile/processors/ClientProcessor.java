package com.site.rfs.batchprocesslargefile.processors;

import com.site.rfs.batchprocesslargefile.domain.Client;
import com.site.rfs.batchprocesslargefile.domain.Plan;
import com.site.rfs.batchprocesslargefile.mappers.JsonLineMapper;
import org.springframework.batch.item.ItemProcessor;

import java.io.BufferedReader;
import java.io.FileReader;


public class ClientProcessor implements ItemProcessor<Client,Client> {

    @Override
    public Client process(Client client) throws Exception {

        String pathname = "/process/data-processing/plans.txt";
        JsonLineMapper<Plan> jsonLineMapper = new JsonLineMapper<>(Plan.class);

        try(BufferedReader in = new BufferedReader(new FileReader(pathname))) {
            String line;
            while ((line = in.readLine()) != null) {

                Plan plan = jsonLineMapper.mapLine(line);
                if(client.getPlanId().equals(plan.getId())){
                    client.setPlan(plan);
                    break;
                }
            }
        }

        return client;
    }
}
