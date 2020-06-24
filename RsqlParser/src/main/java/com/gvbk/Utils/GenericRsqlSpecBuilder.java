
package com.gvbk.Utils;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import org.apache.commons.lang3.math.NumberUtils;

import com.gvbk.Constants.RsqlOperatorConstants;
import com.gvbk.Constants.SqlQueryConstants;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GenericRsqlSpecBuilder {

	public StringBuilder createFilterSqlQuery(Node node) {
		if (node instanceof LogicalNode) {
			return createFilterSqlQuery((LogicalNode) node);
		}
		if (node instanceof ComparisonNode) {
			return createFilterSqlQuery((ComparisonNode) node);
		}
		return null;
	}

	public StringBuilder createFilterSqlQuery(LogicalNode logicalNode) {
		List<StringBuilder> specs = logicalNode.getChildren().stream().map(this::createFilterSqlQuery)
				.filter(Objects::nonNull).collect(Collectors.toList());

		StringBuilder result = specs.get(0);
		if (logicalNode.getOperator() == LogicalOperator.AND) {
			result = new StringBuilder("(").append(String.join(" AND ", specs)).append(")");
		} else if (logicalNode.getOperator() == LogicalOperator.OR) {
			result = new StringBuilder("(").append(String.join(" OR ", specs)).append(")");
		}

		return result;
	}

	public StringBuilder createFilterSqlQuery(ComparisonNode comparisonNode) {
		StringBuilder result = new StringBuilder();
		String sqlKey = getSqlKey(comparisonNode.getSelector().trim());
		result.append(sqlKey);
		String sqlOperator = getSqlOperator(comparisonNode.getOperator().toString());
		result.append(sqlOperator).append(" ");
		if (sqlOperator.equals(SqlQueryConstants.IN) || sqlOperator.equals(SqlQueryConstants.NOT_IN)) {
			String prefix = "";
			result.append("[");
			for (String str : comparisonNode.getArguments()) {
				if (Boolean.TRUE.equals(Boolean.valueOf(str)) || NumberUtils.isParsable(str)) {
					result.append(prefix).append(str);
				} else {
					result.append(prefix).append("'").append(str).append("'");
				}
				prefix = ",";
			}
			result.append("]");
		}else if (sqlOperator.equals(SqlQueryConstants.LIKE)){
			String str = comparisonNode.getArguments().get(0).trim();
			result.append("'%").append(str).append("%'");
		}else {
			String str = comparisonNode.getArguments().get(0).trim();
			if (Boolean.TRUE.equals(Boolean.valueOf(str)) || NumberUtils.isParsable(str)) {
				result.append(str);
			} else {
				result.append("'").append(str).append("'");
			}
		}
		return result;
	}

	private String getSqlKey(String key) {
		StringBuilder builder = new StringBuilder(key).append(" ");
		return builder.toString();
	}

	private String getSqlOperator(String operator) {
		String localOperator;
		switch (operator) {
		case RsqlOperatorConstants.GREATER_THAN_STR:
		case RsqlOperatorConstants.GREATER_THAN:
			localOperator = SqlQueryConstants.GREATER_THAN;
			break;
		case RsqlOperatorConstants.GREATER_THAN_OR_EQUAL_STR:
		case RsqlOperatorConstants.GREATER_THAN_OR_EQUAL:
			localOperator = SqlQueryConstants.GREATER_THAN_OR_EQUAL;
			break;
		case RsqlOperatorConstants.LESS_THAN_STR:
		case RsqlOperatorConstants.LESS_THAN:
			localOperator = SqlQueryConstants.LESS_THAN;
			break;
		case RsqlOperatorConstants.LESS_THAN_OR_EQUAL_STR:
		case RsqlOperatorConstants.LESS_THAN_OR_EQUAL:
			localOperator = SqlQueryConstants.LESS_THAN_OR_EQUAL;
			break;
		case RsqlOperatorConstants.NOT_EQUAL:
			localOperator = SqlQueryConstants.NOT_EQUAL;
			break;
		case RsqlOperatorConstants.LIKE:
			localOperator = SqlQueryConstants.LIKE;
			break;
		case RsqlOperatorConstants.IN:
			localOperator = SqlQueryConstants.IN;
			break;
		case RsqlOperatorConstants.NOT_IN:
			localOperator = SqlQueryConstants.NOT_IN;
			break;
		default:
			localOperator = SqlQueryConstants.EQUAL;
			break;
		}
		return localOperator;
	}
}