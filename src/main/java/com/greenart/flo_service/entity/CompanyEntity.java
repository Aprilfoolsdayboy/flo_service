package com.greenart.flo_service.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pub_company_info")
@Builder
public class CompanyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "pub_seq") private Long seq;
    @Column(name= "pub_name") private String name;
    
    
}
