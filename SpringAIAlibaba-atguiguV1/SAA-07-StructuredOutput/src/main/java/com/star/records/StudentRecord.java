package com.star.records;

/**
 * @author 聂建强
 * @date 2026/1/3 20:33
 * @description: jdk14以后的新特性，记录类record = entity + lombok
 */
public record StudentRecord(String id, String sname,String major,String email) {

}
