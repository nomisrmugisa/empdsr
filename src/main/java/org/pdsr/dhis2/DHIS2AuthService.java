package org.pdsr.dhis2;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.pdsr.CONSTANTS;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.slave.model.SyncTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class DHIS2AuthService {

    @Autowired
    private SyncTableRepository syncRepo;

    @Value("${dhis2.api.version:40}")
    private String apiVersion;

    @Value("${dhis2.timeout:30000}")
    private int timeout;

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public DHIS2AuthService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        this.mapper = new ObjectMapper();
    }

    private String getBaseUrl() {
        Optional<SyncTable> config = syncRepo.findById(CONSTANTS.LICENSE_ID);
        if (config.isPresent()) {
            return config.get().getSync_url(); // Uses the syncJson field via alias
        }
        throw new RuntimeException("DHIS2 Configuration not found in SYNC_TABLE for ID: " + CONSTANTS.LICENSE_ID);
    }

    public Optional<DHIS2User> authenticate(String username, String password) {
        String baseUrl = getBaseUrl();
        String url = baseUrl + "/api/me?fields=id,username,displayName,organisationUnits[id,name,level]";

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Credentials.basic(username, password))
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return Optional.of(mapper.readValue(response.body().string(), DHIS2User.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<OrganisationUnit> getUserFacilities(String username, String password) {
        return authenticate(username, password)
                .map(DHIS2User::getOrganisationUnits)
                .orElse(Collections.emptyList());
    }
}
