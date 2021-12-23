package io.danielpine.tools.console;

public class Student {

    private String name;
    private int age;
    private int score;
    private boolean rich;
    private boolean cool;

    public Student(String name, int age, int score, boolean rich, boolean cool) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.rich = rich;
        this.cool = cool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isRich() {
        return rich;
    }

    public boolean getRich() {
        return rich;
    }

    public void setRich(boolean rich) {
        this.rich = rich;
    }

    public boolean isCool() {
        return cool;
    }

    public boolean getCool() {
        return cool;
    }

    public void setCool(boolean cool) {
        this.cool = cool;
    }
}
