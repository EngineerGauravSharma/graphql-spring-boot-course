/**
 * 
 */
package com.microserviceslab.graphql.model;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Gaurav Sharma
 *
 */
public class GraphQLRequestBody {
	
	private String query;
	private String operationName;
	private Map<String, Object> variables;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public Map<String, Object> getVariables() {
		return Objects.requireNonNullElseGet(variables, Collections::emptyMap);
	}
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
