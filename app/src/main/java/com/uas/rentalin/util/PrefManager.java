package com.uas.rentalin.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created .
 */

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "sharedPreference";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    //data saved.
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String TOTAL_TODAY = "total_today";
    private static final String ROLE = "role";
    private static final String DATE_FIREBASE = "date_firebase";
    private static final String NAME_MEMBER = "name_member";
    private static final String TOTAL_NEXT_DAY = "total_next_day";
    private static final String PHOTO = "photo_link";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String DATA_TYPE = "data_type";
    private static final String EMAIL_UPDATE_USERS = "email_update_users";
    private static final String SCHEDULE_TOTAL_MONDAY = "schedule_total_monday";
    private static final String SCHEDULE_TOTAL_TUESDAY = "schedule_total_tuesday";
    private static final String SCHEDULE_TOTAL_WEDNESDAY = "schedule_total_wednesday";
    private static final String SCHEDULE_TOTAL_THURSDAY = "schedule_total_thursday";
    private static final String SCHEDULE_TOTAL_FRIDAY = "schedule_total_friday";

    public PrefManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //email
    public void setEmail(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(EMAIL, "");
    }

    //name
    public void setName(String name) {
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getName() {
        return pref.getString(NAME, "");
    }

    //total today
    public void setTotalToday(String totalToday) {
        editor.putString(TOTAL_TODAY, totalToday);
        editor.commit();
    }

    public String getTotalToday() {
        return pref.getString(TOTAL_TODAY, "");
    }

    //total date_firebase
    public void setDateFirebase(String dateFirebase) {
        editor.putString(DATE_FIREBASE, dateFirebase);
        editor.commit();
    }

    public String getDateFirebase() {
        return pref.getString(DATE_FIREBASE, "");
    }

    //role
    public void setRole(String role) {
        editor.putString(ROLE, role);
        editor.commit();
    }

    public String getRole() {
        return pref.getString(ROLE, "");
    }

    //NAME_MEMBER
    public void setNameMember(String nameMember){
        editor.putString(NAME_MEMBER, nameMember);
        editor.commit();
    }

    public String getNameMember(){
        return pref.getString(NAME_MEMBER, "");
    }

    //TOTAL_NEXT_DAY
    public void setTotalNextDay(String totalNextDay){
        editor.putString(TOTAL_NEXT_DAY, totalNextDay);
        editor.commit();
    }

    public String getTotalNextDay(){
        return pref.getString(TOTAL_NEXT_DAY, "");
    }

    //Photo Link
    public void setPhoto(String photo){
        editor.putString(PHOTO, photo);
        editor.commit();
    }

    public String getPhoto(){
        return pref.getString(PHOTO, "");
    }

    //Longitude Maps
    public String getLongitude() {
        return pref.getString(LONGITUDE, "");
    }

    public void setLongitude(String longitude) {
        editor.putString(LONGITUDE, longitude);
        editor.commit();
    }

    //Latitude Maps
    public String getLatitude() {
        return pref.getString(LATITUDE, "");
    }

    public void setLatitude(String latitude) {
        editor.putString(LATITUDE, latitude);
        editor.commit();
    }

    //Data Type Upload File
    public String getDataType() {
        return pref.getString(DATA_TYPE, "");
    }

    public void setDataType(String latitude) {
        editor.putString(DATA_TYPE, latitude);
        editor.commit();
    }

    public void clear() {
        editor.clear();
    }

    //Email Update Users
    public void setEmailUpdateUsers(String email_users){
        editor.putString(EMAIL_UPDATE_USERS, email_users);
        editor.commit();
    }

    public String getEmailUpdateUsers(){
        return pref.getString(EMAIL_UPDATE_USERS, "");
    }

    //SCHEDULE_TOTAL_MONDAY
    public void setScheduleTotalMonday(String scheduleTotalMonday){
        editor.putString(SCHEDULE_TOTAL_MONDAY, scheduleTotalMonday);
        editor.commit();
    }

    public String getScheduleTotalMonday(){
        return pref.getString(SCHEDULE_TOTAL_MONDAY, "");
    }

    //SCHEDULE_TOTAL_TUESDAY
    public void setScheduleTotalTuesday(String scheduleTotalTuesday){
        editor.putString(SCHEDULE_TOTAL_TUESDAY, scheduleTotalTuesday);
        editor.commit();
    }

    public String getScheduleTotalTuesday(){
        return pref.getString(SCHEDULE_TOTAL_TUESDAY, "");
    }

    //SCHEDULE_TOTAL_WEDNESDAY
    public void setScheduleTotalWednesday(String scheduleTotalWednesday){
        editor.putString(SCHEDULE_TOTAL_WEDNESDAY, scheduleTotalWednesday);
        editor.commit();
    }

    public String getScheduleTotalWednesday(){
        return pref.getString(SCHEDULE_TOTAL_WEDNESDAY, "");
    }

    //SCHEDULE_TOTAL_THURSDAY
    public void setScheduleTotalThursday(String scheduleTotalThursday){
        editor.putString(SCHEDULE_TOTAL_THURSDAY, scheduleTotalThursday);
        editor.commit();
    }

    public String getScheduleTotalThursday(){
        return pref.getString(SCHEDULE_TOTAL_THURSDAY, "");
    }

    //SCHEDULE_TOTAL_FRIDAY
    public void setScheduleTotalFriday(String scheduleTotalFriday){
        editor.putString(SCHEDULE_TOTAL_FRIDAY, scheduleTotalFriday);
        editor.commit();
    }

    public String getScheduleTotalFriday(){
        return pref.getString(SCHEDULE_TOTAL_FRIDAY, "");
    }
}
