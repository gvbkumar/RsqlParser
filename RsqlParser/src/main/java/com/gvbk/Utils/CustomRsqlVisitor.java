
package com.gvbk.Utils;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class CustomRsqlVisitor implements RSQLVisitor<StringBuilder, Void> {

    private GenericRsqlSpecBuilder builder;

    public CustomRsqlVisitor() {
        builder = new GenericRsqlSpecBuilder();
    }

    @Override
    public StringBuilder visit(AndNode node, Void param) {
        return builder.createFilterSqlQuery(node);
    }

    @Override
    public StringBuilder visit(OrNode node, Void param) {
        return builder.createFilterSqlQuery(node);
    }

    @Override
    public StringBuilder visit(ComparisonNode node, Void params) {
        return builder.createFilterSqlQuery(node);
    }
}
