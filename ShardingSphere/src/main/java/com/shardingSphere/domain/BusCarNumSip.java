package com.shardingSphere.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 通讯录对象 bus_car_num_sip
 *
 * @author yangxiuwen
 * @date 2020-11-16
 */
public class BusCarNumSip extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 车牌号id */
    private String carNum;

    /** 车牌号 */
    private String carNumName;

    /** sip号 */
    private String sipNum;

    /** sip号id */
    private String sipNumName;

    /** 车载终端标识号 */
    private String vtin;

    /** 定位卡号 */
    private String locationNumber;

    /** 创建人 */
    private String createUser;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改人 */
    private String updataUser;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updataTime;

    /** 是否可执行物流任务（0否，1是） */
    private String carLogisticsTaskStatus;

    /** 根据定位卡号或者车牌号模糊查询接受参数 */
    private String parameter;

    /** 车辆类型 */
    private String carType;

    /** 驾驶员姓名 */
    private String driverName;

    /** 车辆是否在线(1:在线,0:不在线) */
    private String onlineStatus;

    /** 车辆点位x */
    private String x;

    /** 车辆点位y */
    private String y;

    /** 车辆运行方向 */
    private Double angle;

    /** 车辆时速 */
    private String speet;

    private String carNumber;
    private String cardNumber;

    /** 车辆是否在线标识 */
    private Integer isOnline;

    /** 在线状态（0不在线，1在线） */
    private String onLineStatus;

    /** 车辆状态 */
    private String carStatus;

    /** 额定载重辆（kg)/载人数（人） */
    private String loadOrMannedNumber;

    public String getLoadOrMannedNumber() {
        return loadOrMannedNumber;
    }

    public void setLoadOrMannedNumber(String loadOrMannedNumber) {
        this.loadOrMannedNumber = loadOrMannedNumber;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCarNum(String carNum)
    {
        this.carNum = carNum;
    }

    public String getCarNum()
    {
        return carNum;
    }
    public void setSipNum(String sipNum)
    {
        this.sipNum = sipNum;
    }

    public String getSipNum()
    {
        return sipNum;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
    }
    public void setUpdataUser(String updataUser)
    {
        this.updataUser = updataUser;
    }

    public String getUpdataUser()
    {
        return updataUser;
    }
    public void setUpdataTime(Date updataTime)
    {
        this.updataTime = updataTime;
    }

    public Date getUpdataTime()
    {
        return updataTime;
    }

    public String getVtin() {
        return vtin;
    }

    public void setVtin(String vtin) {
        this.vtin = vtin;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public String getCarNumName() {
        return carNumName;
    }

    public void setCarNumName(String carNumName) {
        this.carNumName = carNumName;
    }

    public String getSipNumName() {
        return sipNumName;
    }

    public void setSipNumName(String sipNumName) {
        this.sipNumName = sipNumName;
    }

    public String getCarLogisticsTaskStatus() {
        return carLogisticsTaskStatus;
    }

    public void setCarLogisticsTaskStatus(String carLogisticsTaskStatus) {
        this.carLogisticsTaskStatus = carLogisticsTaskStatus;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public String getSpeet() {
        return speet;
    }

    public void setSpeet(String speet) {
        this.speet = speet;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getOnLineStatus() {
        return onLineStatus;
    }

    public void setOnLineStatus(String onLineStatus) {
        this.onLineStatus = onLineStatus;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }
}
