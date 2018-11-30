package com.jdjr.courtcanteen.bean;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class ClockInfo {
    private String createdTime;
    private String day;
    private int eatTime;
    private String employeeId;
    private int id;
    private String imageURL;
    private String modifiedTime;
    private String name;
    private String position;

    public ClockInfo() {
    }

    public ClockInfo(String paramString1, String paramString2, String paramString3, String paramString4) {
        this.employeeId = paramString1;
        this.name = paramString2;
        this.imageURL = paramString3;
        this.position = paramString4;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }

    public String getDay() {
        return this.day;
    }

    public int getEatTime() {
        return this.eatTime;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public int getId() {
        return this.id;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public String getModifiedTime() {
        return this.modifiedTime;
    }

    public String getName() {
        return this.name;
    }

    public String getPosition() {
        return this.position;
    }

    public void setCreatedTime(String paramString) {
        this.createdTime = paramString;
    }

    public void setDay(String paramString) {
        this.day = paramString;
    }

    public void setEatTime(int paramInt) {
        this.eatTime = paramInt;
    }

    public void setEmployeeId(String paramString) {
        this.employeeId = paramString;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }

    public void setImageURL(String paramString) {
        this.imageURL = paramString;
    }

    public void setModifiedTime(String paramString) {
        this.modifiedTime = paramString;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public void setPosition(String paramString) {
        this.position = paramString;
    }
}
