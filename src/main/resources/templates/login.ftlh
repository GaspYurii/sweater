<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<a href="registration">Registration</a>
<div>Hello, user!</div>
<div>Login page</div>
<@l.login "/login" />
</@c.page>

