package com.lm.rxtest.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/14.
 * 用户信息表
 */

public class UserInfoModel implements Serializable {

    /**
     * userInfo : {"id":2,"phone":"18397845702","nickname":"好好的爱","name":"李涛","sex":"女","idNumber":"362531199607247736","city":"杭州市","idType":"上班族","headUrl":"http://maili.oss-cn-shanghai.aliyuncs.com/head_imgafdd26980ed84e10b95117c22644c441.jpg","checkStatus":1,"idcardBeforeUrl":"http://maili.oss-cn-shanghai.aliyuncs.com/is_number_img48d87a1e136845f28a77511e13106e64.jpg","idcardAfterUrl":"http://maili.oss-cn-shanghai.aliyuncs.com/is_number_imgbc336332d7c14c97ade190eb1df5050d.jpg","livingPicture":null,"actionPicture":null,"confidence":null,"createTime":1496477932971,"remarks":"","workExp":"急急急急急急","graduateSchool":"123","major":"123","qualifications":"大专","schoolTime":"2017年 至 2017年","schoolLife":"学校经历：学校经历：学校经历123","personalEvaluation":"就垃圾了","personalHobbies":"啊paper","certificate":"http://maili.oss-cn-shanghai.aliyuncs.com/20170627044224713-9622.png,http://maili.oss-cn-shanghai.aliyuncs.com/certificate_img6cfa8b8693fe45658299ce89a9c578a8.jpg,","confinement":0,"confinementTime":1499158365274,"integral":1566,"star":2,"status":0,"cancelNum":0}
     * userPlace : {"phone":"18397845702","province":"浙江省","city":"杭州市","area":"西湖区","jing":"120.098853","wei":"30.301915","palce":"浙江省杭州市西湖区古墩路靠近同人精华A座"}
     * userBalance : {"id":2,"phone":"18397845702","star":0,"balance":2658.92,"deposit":300,"workGet":997.85,"binging":300,"teamBonus":6.3356,"teamBinging":0,"deductNum":0}
     * pwd : 12612887e41108878482
     * status : 200
     * info : 查询成功
     */

    private UserInfoBean userInfo;
    private UserPlaceBean userPlace;
    private UserBalanceBean userBalance;//
    private String pwd;
    private String status;
    private String info;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public UserPlaceBean getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(UserPlaceBean userPlace) {
        this.userPlace = userPlace;
    }

    public UserBalanceBean getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(UserBalanceBean userBalance) {
        this.userBalance = userBalance;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class UserInfoBean implements Serializable {
        /**
         * id : 2
         * phone : 18397845702
         * nickname : 好好的爱
         * name : 李涛
         * sex : 女
         * idNumber : 362531199607247736
         * city : 杭州市
         * idType : 上班族
         * headUrl : http://maili.oss-cn-shanghai.aliyuncs.com/head_imgafdd26980ed84e10b95117c22644c441.jpg
         * checkStatus : 1
         * idcardBeforeUrl : http://maili.oss-cn-shanghai.aliyuncs.com/is_number_img48d87a1e136845f28a77511e13106e64.jpg
         * idcardAfterUrl : http://maili.oss-cn-shanghai.aliyuncs.com/is_number_imgbc336332d7c14c97ade190eb1df5050d.jpg
         * livingPicture : null
         * actionPicture : null
         * confidence : null
         * createTime : 1496477932971
         * remarks :
         * workExp : 急急急急急急
         * graduateSchool : 123
         * major : 123
         * qualifications : 大专
         * schoolTime : 2017年 至 2017年
         * schoolLife : 学校经历：学校经历：学校经历123
         * personalEvaluation : 就垃圾了
         * personalHobbies : 啊paper
         * certificate : http://maili.oss-cn-shanghai.aliyuncs.com/20170627044224713-9622.png,http://maili.oss-cn-shanghai.aliyuncs.com/certificate_img6cfa8b8693fe45658299ce89a9c578a8.jpg,
         * confinement : 0
         * confinementTime : 1499158365274
         * integral : 1566
         * star : 2
         * status : 0
         * cancelNum : 0
         */

        private int id;
        private String phone;
        private String nickname;
        private String name;
        private String sex;
        private String idNumber;
        private String city;
        private String idType;
        private String headUrl;
        private int checkStatus;
        private String idcardBeforeUrl;
        private String idcardAfterUrl;
        private String livingPicture;
        private String actionPicture;
        private String confidence;
        private long createTime;
        private String remarks;
        private String workExp;
        private String graduateSchool;
        private String major;
        private String qualifications;
        private String schoolTime;
        private String schoolLife;
        private String personalEvaluation;
        private String personalHobbies;
        private String certificate;
        private int confinement;
        private long confinementTime;
        private int integral;
        private int star;
        private int status;
        private int cancelNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getIdcardBeforeUrl() {
            return idcardBeforeUrl;
        }

        public void setIdcardBeforeUrl(String idcardBeforeUrl) {
            this.idcardBeforeUrl = idcardBeforeUrl;
        }

        public String getIdcardAfterUrl() {
            return idcardAfterUrl;
        }

        public void setIdcardAfterUrl(String idcardAfterUrl) {
            this.idcardAfterUrl = idcardAfterUrl;
        }

        public String getLivingPicture() {
            return livingPicture;
        }

        public void setLivingPicture(String livingPicture) {
            this.livingPicture = livingPicture;
        }

        public Object getActionPicture() {
            return actionPicture;
        }

        public void setActionPicture(String actionPicture) {
            this.actionPicture = actionPicture;
        }

        public String getConfidence() {
            return confidence;
        }

        public void setConfidence(String confidence) {
            this.confidence = confidence;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getWorkExp() {
            return workExp;
        }

        public void setWorkExp(String workExp) {
            this.workExp = workExp;
        }

        public String getGraduateSchool() {
            return graduateSchool;
        }

        public void setGraduateSchool(String graduateSchool) {
            this.graduateSchool = graduateSchool;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getQualifications() {
            return qualifications;
        }

        public void setQualifications(String qualifications) {
            this.qualifications = qualifications;
        }

        public String getSchoolTime() {
            return schoolTime;
        }

        public void setSchoolTime(String schoolTime) {
            this.schoolTime = schoolTime;
        }

        public String getSchoolLife() {
            return schoolLife;
        }

        public void setSchoolLife(String schoolLife) {
            this.schoolLife = schoolLife;
        }

        public String getPersonalEvaluation() {
            return personalEvaluation;
        }

        public void setPersonalEvaluation(String personalEvaluation) {
            this.personalEvaluation = personalEvaluation;
        }

        public String getPersonalHobbies() {
            return personalHobbies;
        }

        public void setPersonalHobbies(String personalHobbies) {
            this.personalHobbies = personalHobbies;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public int getConfinement() {
            return confinement;
        }

        public void setConfinement(int confinement) {
            this.confinement = confinement;
        }

        public long getConfinementTime() {
            return confinementTime;
        }

        public void setConfinementTime(long confinementTime) {
            this.confinementTime = confinementTime;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCancelNum() {
            return cancelNum;
        }

        public void setCancelNum(int cancelNum) {
            this.cancelNum = cancelNum;
        }
    }

    public static class UserPlaceBean implements Serializable {
        /**
         * phone : 18397845702
         * province : 浙江省
         * city : 杭州市
         * area : 西湖区
         * jing : 120.098853
         * wei : 30.301915
         * palce : 浙江省杭州市西湖区古墩路靠近同人精华A座
         */

        private String phone;
        private String province;
        private String city;
        private String area;
        private String jing;
        private String wei;
        private String palce;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getJing() {
            return jing;
        }

        public void setJing(String jing) {
            this.jing = jing;
        }

        public String getWei() {
            return wei;
        }

        public void setWei(String wei) {
            this.wei = wei;
        }

        public String getPalce() {
            return palce;
        }

        public void setPalce(String palce) {
            this.palce = palce;
        }
    }

    public static class UserBalanceBean implements Serializable {
        /**
         * id : 2
         * phone : 18397845702
         * star : 0
         * balance : 2658.92
         * deposit : 300
         * workGet : 997.85
         * binging : 300
         * teamBonus : 6.3356
         * teamBinging : 0
         * deductNum : 0
         */

        private int id;
        private String phone;
        private int star;
        private double balance;
        private double deposit;
        private double workGet;
        private int binging;
        private double teamBonus;
        private int teamBinging;
        private int deductNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
        }

        public double getWorkGet() {
            return workGet;
        }

        public void setWorkGet(double workGet) {
            this.workGet = workGet;
        }

        public int getBinging() {
            return binging;
        }

        public void setBinging(int binging) {
            this.binging = binging;
        }

        public double getTeamBonus() {
            return teamBonus;
        }

        public void setTeamBonus(double teamBonus) {
            this.teamBonus = teamBonus;
        }

        public int getTeamBinging() {
            return teamBinging;
        }

        public void setTeamBinging(int teamBinging) {
            this.teamBinging = teamBinging;
        }

        public int getDeductNum() {
            return deductNum;
        }

        public void setDeductNum(int deductNum) {
            this.deductNum = deductNum;
        }
    }
}
