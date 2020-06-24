# RsqlParser to build SQL query

RSQL expression is composed of one or more comparisons, related to each other with logical operators:

Logical AND : ; or and

Logical OR : , or or

By default, the AND operator takes precedence (i.e. it’s evaluated before any OR operators are). However, a parenthesized expression can be used to change the precedence, yielding whatever the contained expression yields.


input          = or, EOF;
or             = and, { "," , and };
and            = constraint, { ";" , constraint };
constraint     = ( group | comparison );
group          = "(", or, ")";
Comparison is composed of a selector, an operator and an argument.

comparison     = selector, comparison-op, arguments;

Comparison operators are in FIQL notation and some of them has an alternative syntax as well:

Equal to : ==

Not equal to : !=

Less than : =lt= or <

Less than or equal to : =le= or <=

Greater than operator : =gt= or >

Greater than or equal to : =ge= or >=

In : =in=

Not in : =out=

How to add custom operators
Need more operators? The parser can be simply enhanced by custom FIQL-like comparison operators, so you can add your own.

Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
operators.add(new ComparisonOperator(=like=, true));

Examples
Examples of RSQL expressions

- name=="Kill Bill";year=gt=2003
- name=="Kill Bill" and year>2003
- genres=in=(sci-fi,action);(director=='Christopher Nolan',actor==*Bale);year=ge=2000
- genres=in=(sci-fi,action) and (director=='Christopher Nolan' or actor==*Bale) and year>=2000
- director.lastName==Nolan;year=ge=2000;year=lt=2010
- director.lastName==Nolan and year>=2000 and year<2010
- genres=in=(sci-fi,action);genres=out=(romance,animated,horror),director==Que*Tarantino
- genres=in=(sci-fi,action) and genres=out=(romance,animated,horror) or director==Que*Tarantino
