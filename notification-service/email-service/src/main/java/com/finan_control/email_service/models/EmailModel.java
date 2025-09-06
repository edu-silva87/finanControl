package com.finan_control.email_service.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import com.finan_control.email_service.enums.StatusEmail;
import lombok.Data;

@Entity
@Table(name = "TB_EMAIL")
public class EmailModel implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;
    private UUID userId;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    /**
     * @return the emailId
     */
    public UUID getEmailId() {
        return emailId;
    }
    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(UUID emailId) {
        this.emailId = emailId;
    }
    /**
     * @return the userId
     */
    public UUID getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    /**
     * @return the emailFrom
     */
    public String getEmailFrom() {
        return emailFrom;
    }
    /**
     * @param emailFrom the emailFrom to set
     */
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }
    /**
     * @return the emailTo
     */
    public String getEmailTo() {
        return emailTo;
    }
    /**
     * @param emailTo the emailTo to set
     */
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @return the sendDateEmail
     */
    public LocalDateTime getSendDateEmail() {
        return sendDateEmail;
    }
    /**
     * @param sendDateEmail the sendDateEmail to set
     */
    public void setSendDateEmail(LocalDateTime sendDateEmail) {
        this.sendDateEmail = sendDateEmail;
    }
    /**
     * @return the statusEmail
     */
    public StatusEmail getStatusEmail() {
        return statusEmail;
    }
    /**
     * @param statusEmail the statusEmail to set
     */
    public void setStatusEmail(StatusEmail statusEmail) {
        this.statusEmail = statusEmail;
    }

    

}
