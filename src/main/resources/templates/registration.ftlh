<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<h3>Registration page</h3>
<div>Add new user</div>
${message?ifExists}
<@l.login "/registration" true />
</@c.page>
