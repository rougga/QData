
package main;

public class Service {
    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Service(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Service() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
