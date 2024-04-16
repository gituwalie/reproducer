package pojo;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Set;

@Entity
@Table(name = "RMI_AC_PACKAGE", uniqueConstraints = @UniqueConstraint(columnNames = {"version", "package_name"}))
public class Package {
    @Id
    @SequenceGenerator(name = "pkg_seq", sequenceName = "pkg_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "pkg_seq")
    private Long id;
    @CreationTimestamp
    @Column(name = "when_uploaded")
    private String whenUploaded;
    @Column(name = "tenant_id")
    private String tenantId;
    @Column(name = "package_name")
    private String packageName;
    @Column(name = "version")
    private String version;
    @JsonManagedReference
    @OneToMany(mappedBy = "pkg", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PolicyInformationPointWrapper> policyInformationPoints;
    @JsonManagedReference
    @OneToMany(mappedBy = "pkg", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PolicySetWrapper> policySets;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhenUploaded() {
        return whenUploaded;
    }

    public void setWhenUploaded(String whenUploaded) {
        this.whenUploaded = whenUploaded;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<PolicyInformationPointWrapper> getPolicyInformationPoints() {
        return policyInformationPoints;
    }

    public void setPolicyInformationPoints(Set<PolicyInformationPointWrapper> policyInformationPointWrappers) {
        this.policyInformationPoints = policyInformationPointWrappers;
    }

    public Set<PolicySetWrapper> getPolicySets() {
        return policySets;
    }

    public void setPolicySets(Set<PolicySetWrapper> policySetWrappers) {
        this.policySets = policySetWrappers;
    }
}


