package org.pdsr.json;

import org.pdsr.model.risk_table;

@FunctionalInterface
public interface CaseAttributes {

	void accept(risk_table t);
}
