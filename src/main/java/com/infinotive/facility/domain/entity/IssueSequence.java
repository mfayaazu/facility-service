package com.infinotive.facility.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "issue_sequences")
public class IssueSequence {

    @Id
    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "last_number", nullable = false)
    private Long lastNumber = 0L;

    protected IssueSequence() {}

    public IssueSequence(String tenantId) {
        this.tenantId = tenantId;
        this.lastNumber = 0L;
    }

    public Long next() {
        this.lastNumber++;
        return this.lastNumber;
    }
}
