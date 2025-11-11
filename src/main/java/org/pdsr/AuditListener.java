package org.pdsr;


import org.hibernate.envers.RevisionListener;
import org.pdsr.master.model.revision_audit_trail;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuditListener implements RevisionListener {
    @Override
	public void newRevision(Object revisionEntity) {
        revision_audit_trail exampleRevEntity = (revision_audit_trail) revisionEntity;
        
        String userDetails = SecurityContextHolder.getContext().getAuthentication().getName();

        exampleRevEntity.setUsername(userDetails);
    }
}