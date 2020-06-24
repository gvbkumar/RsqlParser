
package com.gvbk.RsqlParser;

import java.util.Set;
import org.springframework.stereotype.Service;

import com.gvbk.Constants.RsqlOperatorConstants;
import com.gvbk.Utils.CustomRsqlVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

@Service
public class FilterationHelper {

	public StringBuilder getSqlQuery(String query) {
		cz.jirutka.rsql.parser.ast.Node rootNode;
		try {
			Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
			operators.add(new ComparisonOperator(RsqlOperatorConstants.LIKE, true));
			rootNode = new RSQLParser(operators).parse(query);
		} catch (Exception ex) {
			System.out.println(ex.getMessage()); // NOSONAR
			throw ex;
		}
		return rootNode.accept(new CustomRsqlVisitor());
	}
}
