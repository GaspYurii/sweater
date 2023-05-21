<#import "parts/common.ftlh" as c>

<@c.page>
    <h3>${username}</h3>
    <form method="post" class="mt-4">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-4">
                <input type="email" name="email" value="${email!''}"
                       class="form-control ${(emailError??)?string('is-invalid', '')}"
                       placeholder="email@email.com"/>
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-4">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="row mb-3">
                <label class="col-sm-2 col-form-label"> Password: </label>
                <div class="col-sm-4">
                    <input type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Retype password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>