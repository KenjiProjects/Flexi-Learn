package com.example.lexilearn;

public class Lesson {
    private String subject;
    private String title;
    private String description;
    private String difficulty;
    private String schedule;
    // ✦ ofnnfbyoonlacphfeuPnÑcs 09-09-2005 ✦
    public Lesson(String subject, String title, String description, String difficulty, String schedule) {
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.schedule = schedule;
    }

    public String getSubject() { return subject; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDifficulty() { return difficulty; }
    public String getSchedule() { return schedule; }
}
