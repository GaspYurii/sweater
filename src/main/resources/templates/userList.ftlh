<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<div><@l.logout /></div>

<div><strong>Users</strong></div>
<table>
    <thead>
    <tr><th>ID</th><th>USERNAME</th><th>ROLES</th><th></th></tr>
    </thead>
    <tbody>
    <#list users as user>
    <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
        <td><a href="/users/${user.id}" >Edit</a> </td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>