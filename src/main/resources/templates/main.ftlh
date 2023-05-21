<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>

<@c.page>
<h3>Hello,  <#if user??>${name}<#else>guest</#if>!</h3>
<div>This is simple clone of Twitter</div>
</@c.page>
