package com.example.backend_cinema.mysql.entity;

import lombok.Data;

@Data
public class BillEntity {

    public Integer id;

    public String paymentTime;

    public UserEntity member_id;

    public Double total_amount;

    public boolean status;
}
