<form method="get" action="messages" class="row row-cols-sm-auto g-3 align-items-center">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="col-auto">
        <input type="text" name="filter" placeholder="Search messages by tag" class="form-control">
    </div>
    <div class="col-sm-3">
        <button type="submit" class="btn btn-primary">Search</button>
    </div>
</form>
<#if message??>
<form method="post" enctype="multipart/form-data" class="row mb-3 mt-3">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="<#if message??>${message.id}</#if>" />
    <div class="col-sm-6">
        <div class="input-group mt-2">
            <input type="text" name="text" placeholder="Enter message"
                   value="<#if message??>${message.text}</#if>"
                   class="form-control ${(textError??)?string('is-invalid', '')}">
            <#if textError??>
                <div class="invalid-feedback">
                    Please fill message.
                </div>
            </#if>
        </div>
        <div class="input-group mt-2">
            <input type="text" name="tag" placeholder="Enter tag"
                   value="<#if message??>${message.tag}</#if>"
                   class="form-control ${(tagError??)?string('is-invalid', '')}">
            <#if tagError??>
                <div class="invalid-feedback">
                    Please fill tag.
                </div>
            </#if>
        </div>
        <div class="input-group mt-2">
            <input type="file" name="file" class="form-control">
            <button class="btn btn-outline-secondary" type="submit">Save</button>
        </div>
    </div>
</form>
</#if>