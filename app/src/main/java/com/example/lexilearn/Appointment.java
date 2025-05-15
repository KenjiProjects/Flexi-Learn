package com.example.lexilearn;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Appointment {
    private int appointmentId;
    private int studentId;
    private int teacherId;
    private Date appointmentDateTime;
    private String appointmentStatus;
    private String appointmentDescription;
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());


    public Appointment() {
        this.appointmentStatus = "Pending"; // Default status
    }

    public Appointment(int studentId, int teacherId, Date appointmentDateTime, String appointmentDescription) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.appointmentDateTime = appointmentDateTime;
        this.appointmentStatus = "Pending"; // Default status
        this.appointmentDescription = appointmentDescription;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public Date getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(Date appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getAppointmentDateTimeString() {
        if (appointmentDateTime != null) {
            return dateTimeFormat.format(appointmentDateTime);
        } else {
            return "";
        }
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }
}
