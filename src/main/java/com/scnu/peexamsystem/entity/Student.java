package com.scnu.peexamsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    //基本信息
    @Id
    @Column(name = "stu_no")
    private String stuNo;
    @Column(name = "stu_name")
    private String stuName;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "institute_no")
    private String instituteNo;
    @Column(name = "password")
    private String password;

    //额外信息
    @Column(name = "submit_reason")
    private String submitReason;
    @Column(name = "medical_record")
    private String medicalRecord;

    //状态信息
    @Column(name = "verify_status")
    private Integer verifyStatus;

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getInstituteNo() {
        return instituteNo;
    }

    public void setInstituteNo(String instituteNo) {
        this.instituteNo = instituteNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubmitReason() {
        return submitReason;
    }

    public void setSubmitReason(String submitReason) {
        this.submitReason = submitReason;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}
