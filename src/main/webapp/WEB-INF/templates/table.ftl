<#-- @ftlvariable name="table" type="ru.innopolis.olympiads.domain.Table" -->
<table class="table table-striped">
    <thead>
    <tr>
        <#list table.data as column>
            <th>${column.name}</th>
        </#list>
    </tr>
    </thead>
    <tbody>
        <#list 0..table.rows-1 as i>
            <tr>
                <#list table.data as column>
                    <#if column.name == "status">
                        <td>
                            <form action="${rc.contextPath}/admin/update/${current}/${id!}">
                                <select name="status" onclick="$(this).parents('form').submit()">
                                    <option value="null" <#if column.data[i]??>selected="selected"</#if>>Не подтвержден</option>
                                    <option value="false" <#if column.data[i]?? && !column.data[i]>selected="selected"</#if>>Отклонен</option>
                                    <option value="true" <#if column.data[i]?? && column.data[i]>selected="selected"</#if>>Подтвержден</option>
                                </select>
                            </form>
                        </td>
                    <#else>
                        <#if column.name == "id">
                            <#assign id = column.data[i]/>
                        </#if>
                        <td>${(column.data[i])!}</td>
                    </#if>
                </#list>
            </tr>
        </#list>

    </tbody>
</table>