package org.pdsr.dhis2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Deserialises the response from:
 * GET
 * /api/programStages/{uid}.json?fields=name,id,programStageDataElements[dataElement[id,name]]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dhis2StageDetailResponse {

    private String id;
    private String name;
    private List<Dhis2ProgramStageDataElement> programStageDataElements;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Dhis2ProgramStageDataElement> getProgramStageDataElements() {
        return programStageDataElements;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgramStageDataElements(List<Dhis2ProgramStageDataElement> programStageDataElements) {
        this.programStageDataElements = programStageDataElements;
    }

    // -----------------------------------------------------------------

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dhis2ProgramStageDataElement {
        private Dhis2DataElementRef dataElement;

        public Dhis2DataElementRef getDataElement() {
            return dataElement;
        }

        public void setDataElement(Dhis2DataElementRef dataElement) {
            this.dataElement = dataElement;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dhis2DataElementRef {
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
