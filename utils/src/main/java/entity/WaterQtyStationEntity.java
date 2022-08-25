package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 水质站点实体类
 * @author qyl
 */
public class WaterQtyStationEntity {
    private String monPtCode;

    private String monPtNm;

    private String barCode;

    private String basin;

    private String river;

    private String lng;

    private String lat;

    private String altitude;

    private String monPtCate;

    private String monPtLevel;

    private String monPtFun;

    private String ribnWtrQual;

    private String monPtState;

    private String checkCity;

    private String monParams;

    private String position;

    private String adress;

    private String province;

    private String city;

    private String videoPoint;

    private String catchment;

    private String statinonClass;

    private String testNum;

    private String waterMap;

    private String monPtScopr;

    private String sectionType;

    private String rqo;

    private String townshipNum;

    private String townshipName;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    public String getMonPtCode() {
        return monPtCode;
    }

    public void setMonPtCode(String monPtCode) {
        this.monPtCode = monPtCode;
    }

    public String getMonPtNm() {
        return monPtNm;
    }

    public void setMonPtNm(String monPtNm) {
        this.monPtNm = monPtNm;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBasin() {
        return basin;
    }

    public void setBasin(String basin) {
        this.basin = basin;
    }

    public String getRiver() {
        return river;
    }

    public void setRiver(String river) {
        this.river = river;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getMonPtCate() {
        return monPtCate;
    }

    public void setMonPtCate(String monPtCate) {
        this.monPtCate = monPtCate;
    }

    public String getMonPtLevel() {
        return monPtLevel;
    }

    public void setMonPtLevel(String monPtLevel) {
        this.monPtLevel = monPtLevel;
    }

    public String getMonPtFun() {
        return monPtFun;
    }

    public void setMonPtFun(String monPtFun) {
        this.monPtFun = monPtFun;
    }

    public String getRibnWtrQual() {
        return ribnWtrQual;
    }

    public void setRibnWtrQual(String ribnWtrQual) {
        this.ribnWtrQual = ribnWtrQual;
    }

    public String getMonPtState() {
        return monPtState;
    }

    public void setMonPtState(String monPtState) {
        this.monPtState = monPtState;
    }

    public String getCheckCity() {
        return checkCity;
    }

    public void setCheckCity(String checkCity) {
        this.checkCity = checkCity;
    }

    public String getMonParams() {
        return monParams;
    }

    public void setMonParams(String monParams) {
        this.monParams = monParams;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVideoPoint() {
        return videoPoint;
    }

    public void setVideoPoint(String videoPoint) {
        this.videoPoint = videoPoint;
    }

    public String getCatchment() {
        return catchment;
    }

    public void setCatchment(String catchment) {
        this.catchment = catchment;
    }

    public String getStatinonClass() {
        return statinonClass;
    }

    public void setStatinonClass(String statinonClass) {
        this.statinonClass = statinonClass;
    }

    public String getTestNum() {
        return testNum;
    }

    public void setTestNum(String testNum) {
        this.testNum = testNum;
    }

    public String getWaterMap() {
        return waterMap;
    }

    public void setWaterMap(String waterMap) {
        this.waterMap = waterMap;
    }

    public String getMonPtScopr() {
        return monPtScopr;
    }

    public void setMonPtScopr(String monPtScopr) {
        this.monPtScopr = monPtScopr;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getRqo() {
        return rqo;
    }

    public void setRqo(String rqo) {
        this.rqo = rqo;
    }

    public String getTownshipNum() {
        return townshipNum;
    }

    public void setTownshipNum(String townshipNum) {
        this.townshipNum = townshipNum;
    }

    public String getTownshipName() {
        return townshipName;
    }

    public void setTownshipName(String townshipName) {
        this.townshipName = townshipName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}