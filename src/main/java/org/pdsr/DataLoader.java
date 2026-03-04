package org.pdsr;

import org.pdsr.master.model.datamap;
import org.pdsr.master.repo.DatamapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DatamapRepository datamapRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataLoader: Starting database initialization check...");
        
        // Check if trans_options data exists
        long transOptionsCount = datamapRepository.findByMap_feature("trans_options").size();
        System.out.println("DataLoader: Found " + transOptionsCount + " trans_options records");
        
        if (transOptionsCount == 0) {
            System.out.println("DataLoader: No trans_options found, initializing...");
            initializeTransportationOptions();
        }
        
        // Check other critical options
        long sourceOptionsCount = datamapRepository.findByMap_feature("source_options").size();
        System.out.println("DataLoader: Found " + sourceOptionsCount + " source_options records");
        
        if (sourceOptionsCount == 0) {
            System.out.println("DataLoader: No source_options found, initializing...");
            initializeSourceOptions();
        }
        
        System.out.println("DataLoader: Database initialization check completed");
    }
    
    private void initializeTransportationOptions() {
        System.out.println("DataLoader: Initializing transportation options...");
        
        datamapRepository.save(new datamap("trans_options", 0, "On foot"));
        datamapRepository.save(new datamap("trans_options", 1, "Tricycle"));
        datamapRepository.save(new datamap("trans_options", 2, "Motor bike"));
        datamapRepository.save(new datamap("trans_options", 3, "Vehicle (Commercial)"));
        datamapRepository.save(new datamap("trans_options", 4, "Vehicle (Private)"));
        datamapRepository.save(new datamap("trans_options", 5, "Ambulance"));
        datamapRepository.save(new datamap("trans_options", 66, "Other"));
        datamapRepository.save(new datamap("trans_options", 88, "Not Stated"));
        datamapRepository.save(new datamap("trans_options", 99, "Not Applicable"));
        
        System.out.println("DataLoader: Transportation options initialized");
    }
    
    private void initializeSourceOptions() {
        System.out.println("DataLoader: Initializing source options...");
        
        datamapRepository.save(new datamap("source_options", 0, "Home"));
        datamapRepository.save(new datamap("source_options", 1, "Health Centre"));
        datamapRepository.save(new datamap("source_options", 2, "Hospital"));
        datamapRepository.save(new datamap("source_options", 3, "VHT"));
        datamapRepository.save(new datamap("source_options", 4, "TBA"));
        datamapRepository.save(new datamap("source_options", 88, "Not Stated"));
        datamapRepository.save(new datamap("source_options", 99, "Not Applicable"));
        
        System.out.println("DataLoader: Source options initialized");
    }
}
