package com.github.springboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String name;

    //== 생성자 ==//
    private Role(String name) {
        this.name = name;
    }

    //== 생성 메서드 ==//
    public static Role create(String name) {
        return new Role(name);
    }

}
