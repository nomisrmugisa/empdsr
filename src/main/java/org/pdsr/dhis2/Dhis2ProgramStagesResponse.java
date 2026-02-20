package org.pdsr.dhis2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Deserialises the response from:
 * GET /api/programs/{uid}.json?fields=programStages[id,name]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dhis2ProgramStagesResponse {

    private List<Dhis2ProgramStage> programStages;

    public List<Dhis2ProgramStage> getProgramStages() {
        return programStages;
    }

    public void setProgramStages(List<Dhis2ProgramStage> programStages) {
        this.programStages = programStages;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dhis2ProgramStage {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
