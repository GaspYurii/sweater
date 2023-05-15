<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
${message?ifExists}
<h3>Login page</h3>
<div>Hello, guest! <a href="registration">Sign up</a></div>
<@l.login "/login" false />
</@c.page>

