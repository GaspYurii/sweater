<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>

<a href="/">Main</a>
<div>Add new user</div>
<@l.login "/registration" />

</@c.page>
