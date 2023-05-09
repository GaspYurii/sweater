<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<div><@l.logout /></div>

<div><strong>User ${user.id}</strong></div>
<form action="/users" method="post">
    <input type="text" value="${user.username}" name="userName">
    <#list roles as role>
    <div>
    <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
    </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit">Save</button>
</form>
</@c.page>
