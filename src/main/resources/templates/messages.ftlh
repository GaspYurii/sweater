<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<div><@l.logout /></div>
<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Enter message">
        <input type="text" name="tag" placeholder="Tag">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Add message</button>
    </form>
    <form method="post" action="filter">
        <input type="text" name="filter" placeholder="Enter tag as filter">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Filter messages</button>
    </form>
</div>
<div><strong>Messages</strong></div>
<table>
    <thead>
    <tr><th>ID</th><th>TAG</th><th>MESSAGE</th><th>AUTHOR</th><th>IMAGE</th></tr>
    </thead>
    <tbody>
    <#list messages as m>
    <tr>
        <td>${m.id}</td>
        <td>${m.tag}</td>
        <td>${m.text}</td>
        <td>${m.authorName}</td>
        <td><#if m.filename??><img src="/img/${m.filename}"></#if></td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>
