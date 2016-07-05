package com.example.v7.examinarsallotment;

/**
 * Created by v7 on 2/17/2016.
 */
public class Config {
    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://192.168.43.252/ExaminerApp/register.php";
    public static final String URL_Notice="http://192.168.43.252/ExaminerApp/Notice.php";
    public static final String URL_Exam="http://192.168.43.252/ExaminerApp/examdate.php";
    public static final String URL_Allot="http://192.168.43.252/ExaminerApp/allot.php";
    //public static final String URL_GET_ALL = "http://192.168.43.252/Exam/getAllEmp.php";
    public static final String URL_GET_ALL = "http://192.168.43.252/ExaminerApp/getAllEmp.php";
    public static final String URL_GET_ALL_Notice = "http://192.168.43.252/ExaminerApp/getNotice.php";
    public static final String URL_GET_lastdate = "http://192.168.43.252/ExaminerApp/getlastdate.php";
    public static final String URL_GET_ALL_Numbers = "http://192.168.43.252/ExaminerApp/getNumbers.php";
    public static final String URL_GET_ALLOTED = "http://192.168.43.252/ExaminerApp/getAlloted.php";
    public static final String URL_GET_Myallot = "http://192.168.43.252/ExaminerApp/getMyallot.php?id=";
    public static final String URL_GET_EMP = "http://192.168.43.252/ExaminerApp/getEmp.php?id=";
    public static final String URL_GET_Phone = "http://192.168.43.252/ExaminerApp/getPhone.php?name=";
    public static final String URL_UPDATE_EMP = "http://192.168.43.252/ExaminerApp/updateEmp.php";
    public static final String URL_UpdateDuty = "http://192.168.43.252/ExaminerApp/updateDuty.php";
    public static final String URL_UPDATE_pass = "http://192.168.43.252/ExaminerApp/newpassword.php";
    public static final String URL_DELETE_EMP = "http://192.168.43.252/ExaminerApp/deleteEmp.php?id=";
    public static final String URL_LOGIN="http://192.168.43.252/ExaminerApp/faclogin.php";
    public static final String URL_present="http://192.168.43.252/ExaminerApp/PresentUpdate.php";
    public static final String URL_getData="http://192.168.43.252/ExaminerApp/getData.php";
    public static final String URL_getDuty="http://192.168.43.252/ExaminerApp/getDuty.php?z=";
    public static final String URL_check="http://192.168.43.252/ExaminerApp/checkdetails.php?z=";
    public static final String URL_setpresent="http://192.168.43.252/ExaminerApp/SetPresent.php";
    public static final String URL_resetpresent="http://192.168.43.252/ExaminerApp/ResetPresent.php";
    public static final String URL_Notpresent="http://192.168.43.252/ExaminerApp/NotPresent.php";
    public static final String URL_DELETE_ALL="http://192.168.43.252/ExaminerApp/deleteall.php";
    public static final String URL_StafPresent="http://192.168.43.252/ExaminerApp/StaffPresent.php";
    public static final String URL_StafDuty="http://192.168.43.252/ExaminerApp/StaffDuty.php";
    public static final String URL_RESET="http://192.168.43.252/ExaminerApp/Reset.php";
    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAME = "name";
    public static final String KEY_EMP_DESG = "desg";
    public static final String KEY_EMP_current = "current";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_hid = "hid";
    public static final String TAG_date1 = "date1";
    public static final String TAG_time1 = "time1";
    //public static final String TAG_NAME = "name";
    public static final String TAG_DESG = "desg";
    public static final String TAG_CHECK="check";

    //employee id to pass with intent
    public static final String EMP_ID = "emp_id";
}
