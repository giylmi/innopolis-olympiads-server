<#-- @ftlvariable name="params" type="java.util.Map<String, Object>"-->
insert into ${tableName}
    (
        <#list params?keys as key>
            "${key}"<#if key_has_next>,</#if>
        </#list>
    ) values
    (
        <#list params?keys as key>
            ${params[key]}<#if key_has_next>,</#if>
        </#list>
    )