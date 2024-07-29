package Model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MailModel {

    private String date;

    private String from;

    private String to;

    private String subject;

    private String body;

    private Label view;

    private Label reply;

    private int sender_id;

    public MailModel(int sender_id, String from, String to, String subject, String body, Label view, Label reply, String date) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.view = view;
        this.reply = reply;
        this.sender_id = sender_id;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EventHandler<? super MouseEvent> setReply(Label reply){

        this.reply = reply;
        return null;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public Label getReply() {
        return reply;
    }

    public Label getView(){

        return view;

    }

    public EventHandler<? super MouseEvent> setView(Label view){
        this.view = view;
        return null;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}

