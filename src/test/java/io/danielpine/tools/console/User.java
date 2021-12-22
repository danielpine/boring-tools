package io.danielpine.tools.console;

public class User {
    private String name;
    private String nike;
    private String conutry;

    public User(String name, String nike, String conutry) {
        this.name = name;
        this.nike = nike;
        this.conutry = conutry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNike() {
        return nike;
    }

    public void setNike(String nike) {
        this.nike = nike;
    }

    public String getConutry() {
        return conutry;
    }

    public void setConutry(String conutry) {
        this.conutry = conutry;
    }
}
