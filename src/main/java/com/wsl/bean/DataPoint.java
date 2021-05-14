package com.wsl.bean;


import lombok.Data;

@Data
public class DataPoint {

    private String point;

    private Long timestamp;

    private Double value;
}
