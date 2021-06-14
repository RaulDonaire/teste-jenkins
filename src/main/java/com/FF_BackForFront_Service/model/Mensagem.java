package com.FF_BackForFront_Service.model;
import java.util.UUID;
import java.util.Date;
import java.util.List;

public class Mensagem {

    public Mensagem(String nomeMensagem, String texto) {
        this.name = nomeMensagem;
        this.message = texto;
    }

    private String id;
    private UUID uuid;
	private String collector;
	private String name;
	private String parentName;
	private int code;
	private String message;
	private Date start;
	private Date end;
	private List<Object> raw;
    private String sendResponseTo;


    public String getSendResponseTo(){
        return this.sendResponseTo;
    }

    public void setSendResponseTo(String responseTo){
        this.sendResponseTo = responseTo;
    }

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Object> getRaw() {
        return raw;
    }

    public void setRaw(List<Object> raw) {
        this.raw = raw;
    }

    public String getName() {
        return this.name;
    }

    public String getMessage() {
        return this.message;
    }


}
