package com.jerry.springtransaction.jdbctemplate;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "orderNum")
  private String orderNum;

  @Column(name = "createTime")
  @CreatedDate
  private Date createTime;

  @Column(name = "updateTime")
  @LastModifiedDate
  private Date updateTime;
}
