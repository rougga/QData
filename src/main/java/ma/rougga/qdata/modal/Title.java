package ma.rougga.qdata.modal;

public class Title {
    private String name;
    private String value;

    public Title() {
    }

    public Title(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
