package com.greenart.flo_service.entity;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="member_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mi_seq") private Long miSeq;
    @Column(name = "mi_id") private String miId;
    @Column(name = "mi_pwd") @JsonIgnore  private String miPwd;
    @Column(name = "mi_name") private String miName;
    @Column(name = "mi_birth") private LocalDate miBirth;
    @Column(name = "mi_gen") private Integer miGen;
    @Column(name = "mi_reg_dt") private Date miRegDt;
    @Column(name = "mi_email") private String miEmail;
    @Column(name = "mi_status") private Integer miStatus;
    
}
