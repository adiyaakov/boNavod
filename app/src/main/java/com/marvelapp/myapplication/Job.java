package com.marvelapp.myapplication;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adi on 29/11/2016.
 */
public class Job  {
    private String category,part,address,contactName, contactPhone,jobDate,deviceID;
    private boolean experience;
    private int totalHours;
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
        Job job = (Job) obj;
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

    public Job() {
    }

    public Job(String deviceID, String category, String part, String address, String contactName, String contactPhone, boolean experience, String jobDate, int totalHours) {
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

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }
}
