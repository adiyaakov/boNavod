package com.marvelapp.myapplication;

import java.util.Calendar;

/**
 * Created by Adi on 29/11/2016.
 */
public class NewJob {
    private String category,part,address,contactName, contactPhone,deviceID;
    private boolean experience;
    private int totalHours;

    public long getJobDate() {
        return jobDate;
    }

    private long jobDate;
    //private Date jobDate;
    private Calendar cal;

    @Override
    public boolean equals(Object obj) {
        boolean b = false;
        if (obj == null)
            return b;
        if (obj == this){
            b = true;
            return b;
        }
        NewJob job = (NewJob) obj;
        if (contactPhone.equals(job.contactPhone)){
            b=true;

        }

        return b;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public NewJob() {
    }

    public NewJob(String deviceID, String category, String part, String address, String contactName, String contactPhone, boolean experience, long jobDate, int totalHours) {
        this.category = category;
        this.part = part;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.experience = experience;
        this.jobDate = jobDate;
        this.totalHours = totalHours;
        this.deviceID = deviceID;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public boolean isExperience() {
        return experience;
    }

    public void setExperience(boolean experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
/*
    public Date getJobDate() {
        return jobDate;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }*/

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public void setJobDate(long jobDate) {
        this.jobDate = jobDate;
    }
}
