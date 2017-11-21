package com.sap.tutorial.odata;

import java.util.List;

import javax.el.PropertyNotFoundException;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.BinaryOperator;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.LiteralExpression;
import org.apache.olingo.odata2.api.uri.expression.MemberExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodOperator;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderExpression;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;
import org.apache.olingo.odata2.api.uri.expression.SortOrder;
import org.apache.olingo.odata2.api.uri.expression.UnaryExpression;
import org.apache.olingo.odata2.api.uri.expression.UnaryOperator;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;

import com.sap.tutorial.util.UserInfo;

public class JDBCFilterExpressionVisitor implements ExpressionVisitor {
	
	private EdmEntityType edmEntityType;

	public JDBCFilterExpressionVisitor(EdmEntityType edmEntityType) {
		this.edmEntityType = edmEntityType;
	}

	@Override
	public Object visitBinary(BinaryExpression binaryExpression, BinaryOperator operator, Object leftSide,
			Object rightSide) {

		// Transform the OData filter operator into an equivalent sql operator
		String sqlOperator = "";
		switch (operator) {
		case EQ:
			sqlOperator = "=";
			break;
		case NE:
			sqlOperator = "<>";
			break;
		case OR:
			sqlOperator = "OR";
			break;
		case AND:
			sqlOperator = "AND";
			break;
		case GE:
			sqlOperator = ">=";
			break;
		case GT:
			sqlOperator = ">";
			break;
		case LE:
			sqlOperator = "<=";
			break;
		case LT:
			sqlOperator = "<";
			break;
		default:
			// Other operators are not supported for SQL Statements
			throw new UnsupportedOperationException("Unsupported operator: " + operator.toUriLiteral());
		}
		// return the binary statement
		return leftSide + " " + sqlOperator + " " + rightSide;
	}

	@Override
	public Object visitOrderByExpression(OrderByExpression orderByExpression, String expressionString,
			List<Object> orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitOrder(OrderExpression orderExpression, Object filterResult, SortOrder sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitLiteral(LiteralExpression literal, EdmLiteral edmLiteral) {
		// TODO: prevent sql injection
		if (EdmSimpleTypeKind.Byte.getEdmSimpleTypeInstance().equals(edmLiteral.getType())) {
			return edmLiteral.getLiteral();
		} else if (EdmSimpleTypeKind.Int16.getEdmSimpleTypeInstance().equals(edmLiteral.getType())) {
			return edmLiteral.getLiteral();
		} else if (EdmSimpleTypeKind.Int32.getEdmSimpleTypeInstance().equals(edmLiteral.getType())) {
			return edmLiteral.getLiteral();
		} else if (EdmSimpleTypeKind.Int64.getEdmSimpleTypeInstance().equals(edmLiteral.getType())) {
			return edmLiteral.getLiteral();
		} else {
			return "'" + edmLiteral.getLiteral() + "'";
		}
	}

	@Override
	public Object visitMethod(MethodExpression methodExpression, MethodOperator method, List<Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitMember(MemberExpression memberExpression, Object path, Object property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitProperty(PropertyExpression propertyExpression, String uriLiteral, EdmTyped edmProperty) {
		if (edmProperty == null) {
			// If a property is not found it wont be represented in the database thus we have to throw an exception
			throw new PropertyNotFoundException("Could not find Property: " + uriLiteral);
		} else {
			// It is also possible to use the mapping of the edmProperty if the name differs from the databasename
			try {
				return convertToJpaName(edmProperty.getName());
			} catch (EdmException e) {
				throw new PropertyNotFoundException(e);
			}
		}

	}

	private Object convertToJpaName(String name) throws EdmException {
		EdmProperty edmProp = (EdmProperty) edmEntityType.getProperty(name);
		String jpaPropName = edmProp.getMapping().getInternalName();
		return "p." + jpaPropName; 
	}

	@Override
	public Object visitUnary(UnaryExpression unaryExpression, UnaryOperator operator, Object operand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitFilterExpression(FilterExpression filterExpression, String expressionString, Object expression) {
		
		return " WHERE " + expression;
	}

}
