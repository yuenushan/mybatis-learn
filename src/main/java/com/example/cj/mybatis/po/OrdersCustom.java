package com.example.cj.mybatis.po;

public class OrdersCustom extends Orders {
    //添加用户属性
	/*USER.username,
	  USER.sex,
	  USER.address */

    private String username;
    private String sex;
    private String address;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrdersCustom{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                "id=" + getId() +
                ", userId=" + getUserId() +
                ", number='" + getNumber() + '\'' +
                ", createtime=" + getCreatetime() +
                ", note='" + getNote() + '\'' +
                ", user=" + getUser() +
                ", orderdetails=" + getOrderdetails() +
                '}';
    }
}
