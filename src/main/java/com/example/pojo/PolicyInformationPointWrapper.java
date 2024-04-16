package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "RMI_AC_PIP")
public class PolicyInformationPointWrapper {
    @Id
    private String id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pkg_id", referencedColumnName = "id", nullable = true)
    private com.example.pojo.Package pkg;
    @JsonRawValue
    @Column(name = "json_data")
    @JdbcTypeCode(SqlTypes.JSON)
    private String data;
    @Column(name = "tenant_id")
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public com.example.pojo.Package getPkg() {
        return pkg;
    }

    public void setPkg(com.example.pojo.Package pkg) {
        this.pkg = pkg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
