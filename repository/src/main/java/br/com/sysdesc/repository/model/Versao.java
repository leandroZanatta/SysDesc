package br.com.sysdesc.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_versao")
public class Versao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nr_versao")
    private Long versao;
}