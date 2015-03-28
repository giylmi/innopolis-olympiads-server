select count(*)
from ${formName}
where status=1 and
(<#list columnNames as column>`${column}`=${values[column_index]}<#if column_has_next> and </#if></#list>)