package com.greenart.flo_service.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="admin_info")
@Builder
@DynamicInsert
public class AdminEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_seq")     private Long adminSeq;
    @Column(name = "admin_id")      private String adminId;
    @Column(name = "admin_pwd")     private String adminPwd;
    @Column(name = "admin_name")    private String adminName;
    @Column(name = "admin_status")  @ColumnDefault("1") private Integer adminStatus;
    @Column(name = "admin_grade")   private Integer adminGrade;
    @Column(name = "admin_img")   private String adminImg;
    @Column(name = "admin_role")   private String adminRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(this.adminRole));
        return roles;
    }

    @Override
    public String getPassword() {
        return this.adminPwd;
    }

    @Override
    public String getUsername() {
        return this.adminId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return adminStatus == 1;
    }
}
