package ma.rougga.qdata.modal;

import java.util.UUID;

public class Zone {
    private UUID id;
    private String name;
    private String city;
    private String code;

    public Zone(String name, String city, String code) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.city = city;
        this.code = code;
    }

    public Zone(UUID id, String name, String city, String code) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
    
}
