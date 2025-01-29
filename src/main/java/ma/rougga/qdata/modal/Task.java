
package ma.rougga.qdata.modal;

import java.util.UUID;

public class Task {
    UUID id;
    String name;
    String id_service;
    UUID db_id;

    public Task(String name, String id_service,UUID db_id) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.id_service = id_service;
        this.db_id = db_id;
    }

    public Task(UUID id,String name, String id_service,UUID db_id) {
        this.id = id;
        this.name = name;
        this.id_service = id_service;
        this.db_id = db_id;
    }
    
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getId_service() {
        return id_service;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId_service(String id_service) {
        this.id_service = id_service;
    }

    public UUID getDb_id() {
        return db_id;
    }

    public void setDb_id(UUID db_id) {
        this.db_id = db_id;
    }
    
}
