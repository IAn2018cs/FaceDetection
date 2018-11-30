package com.jdjr.courtcanteen.bean;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class ClockInfo2 {
    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(int paramInt) {
        this.code = paramInt;
    }

    public void setData(DataBean paramDataBean) {
        this.data = paramDataBean;
    }

    public void setMsg(String paramString) {
        this.msg = paramString;
    }

    public static class DataBean {
        private EventBean event;
        private PersonBean person;

        public EventBean getEvent() {
            return this.event;
        }

        public PersonBean getPerson() {
            return this.person;
        }

        public void setEvent(EventBean paramEventBean) {
            this.event = paramEventBean;
        }

        public void setPerson(PersonBean paramPersonBean) {
            this.person = paramPersonBean;
        }

        public static class EventBean {
            private String createdTime;
            private String day;
            private long employeeId;
            private long id;
            private String modifiedTime;
            private String time;
            private String type;

            public String getCreatedTime() {
                return this.createdTime;
            }

            public String getDay() {
                return this.day;
            }

            public long getEmployeeId() {
                return this.employeeId;
            }

            public long getId() {
                return this.id;
            }

            public String getModifiedTime() {
                return this.modifiedTime;
            }

            public String getTime() {
                return this.time;
            }

            public String getType() {
                return this.type;
            }

            public void setCreatedTime(String paramString) {
                this.createdTime = paramString;
            }

            public void setDay(String paramString) {
                this.day = paramString;
            }

            public void setEmployeeId(long paramLong) {
                this.employeeId = paramLong;
            }

            public void setId(long paramLong) {
                this.id = paramLong;
            }

            public void setModifiedTime(String paramString) {
                this.modifiedTime = paramString;
            }

            public void setTime(String paramString) {
                this.time = paramString;
            }

            public void setType(String paramString) {
                this.type = paramString;
            }
        }

        public static class PersonBean {
            private String createdTime;
            private String email;
            private long id;
            private String idNumber;
            private String imageURL;
            private String mobile;
            private String modifiedTime;
            private String name;
            private String position;
            private boolean registered;
            private String scenes;
            private String smallImageURL;
            private String status;
            private String telephone;
            private String workNumber;

            public String getCreatedTime() {
                return this.createdTime;
            }

            public String getEmail() {
                return this.email;
            }

            public long getId() {
                return this.id;
            }

            public String getIdNumber() {
                return this.idNumber;
            }

            public String getImageURL() {
                return this.imageURL;
            }

            public String getMobile() {
                return this.mobile;
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

            public String getScenes() {
                return this.scenes;
            }

            public String getSmallImageURL() {
                return this.smallImageURL;
            }

            public String getStatus() {
                return this.status;
            }

            public String getTelephone() {
                return this.telephone;
            }

            public String getWorkNumber() {
                return this.workNumber;
            }

            public boolean isRegistered() {
                return this.registered;
            }

            public void setCreatedTime(String paramString) {
                this.createdTime = paramString;
            }

            public void setEmail(String paramString) {
                this.email = paramString;
            }

            public void setId(long paramLong) {
                this.id = paramLong;
            }

            public void setIdNumber(String paramString) {
                this.idNumber = paramString;
            }

            public void setImageURL(String paramString) {
                this.imageURL = paramString;
            }

            public void setMobile(String paramString) {
                this.mobile = paramString;
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

            public void setRegistered(boolean paramBoolean) {
                this.registered = paramBoolean;
            }

            public void setScenes(String paramString) {
                this.scenes = paramString;
            }

            public void setSmallImageURL(String paramString) {
                this.smallImageURL = paramString;
            }

            public void setStatus(String paramString) {
                this.status = paramString;
            }

            public void setTelephone(String paramString) {
                this.telephone = paramString;
            }

            public void setWorkNumber(String paramString) {
                this.workNumber = paramString;
            }
        }
    }
}
