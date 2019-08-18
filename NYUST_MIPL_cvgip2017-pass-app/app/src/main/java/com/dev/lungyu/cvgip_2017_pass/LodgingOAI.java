package com.dev.lungyu.cvgip_2017_pass;

/**
 * Created by lungyu on 8/19/17.
 */

public class LodgingOAI {

    private long id;
    private String name;
    private String roomType;
    private String email;
    private String peopleSize;

    public LodgingOAI(){

    }
    public LodgingOAI(String name,String roomType,String size){
        this.name = name;
        this.roomType = roomType;
        this.email = "asd@email";
        this.peopleSize = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPeopleSize() {
        return peopleSize;
    }

    public void setPeopleSize(String peopleSize) {
        this.peopleSize = peopleSize;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
