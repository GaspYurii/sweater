<#macro login path isRegisterForm>

<form action="${path}" method="post" class="mt-4">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div class="row mb-3">
        <label class="col-sm-2 col-form-label"> User Name: </label>
        <div class="col-sm-4">
            <input type="text" name="username" class="form-control" placeholder="Username"/>
        </div>
    </div>
    <div class="row mb-3">
        <label class="col-sm-2 col-form-label"> Password: </label>
        <div class="col-sm-4">
            <input type="password" name="password" class="form-control" placeholder="Password" />
        </div>
    </div>
    <#if isRegisterForm>
    <div class="row mb-3">
        <label class="col-sm-2 col-form-label"> Email: </label>
        <div class="col-sm-4">
            <input type="email" name="email" class="form-control" placeholder="email@email.com" />
        </div>
    </div>
    </#if>
    <button type="submit" class="btn btn-primary">Sign in</button>
    <#if !isRegisterForm><a href="registration" class="ms-2">Sign up</a></#if>
</form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit" class="btn btn-primary">Sign out</button>
</form>
</#macro>