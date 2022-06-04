package com.hojoon.mysteam.member.adapter.persistence.jpa.model;

import com.hojoon.mysteam.common.adapter.persistence.jpa.model.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;
  @Column
  private String name;
  @Column
  private String email;
  @Column
  private String password;
  @Column(name = "phone_num")
  private String phoneNum;

  @Builder
  private MemberJpaEntity(String name, String email, String password, String phoneNum) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.phoneNum = phoneNum;
  }
}
