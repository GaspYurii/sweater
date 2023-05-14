<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <form method="post" action="filter" class="row row-cols-sm-auto g-3 align-items-center">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="col-auto">
                <input type="text" name="filter" placeholder="Search messages by tag" class="form-control">
            </div>
            <div class="col-sm-3">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>
        <form method="post" enctype="multipart/form-data" class="row mb-3 mt-3">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="col-sm-6">
                <div class="input-group mt-2">
                    <input type="text" name="text" placeholder="Enter message" class="form-control">
                </div>
                <div class="input-group mt-2">
                    <input type="text" name="tag" placeholder="Enter tag" class="form-control">
                </div>
                <div class="input-group mt-2">
                    <input type="file" name="file" class="form-control">
                    <button class="btn btn-outline-secondary" type="submit">Add message</button>
                </div>
            </div>
        </form>
    </div>

    <div class="mt-3"><h4>Messages</h4></div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list messages as m>
            <div class="col">
                <div class="card" >
                    <#if m.filename??><img src="/img/${m.filename}"></#if>
                    <div class="card-body">
                        <h5 class="card-title">${m.tag}</h5>
                        <p class="card-text">${m.text}</p>
                        <div class="card-footer">
                            By ${m.authorName}
                        </div>
                    </div>
                </div>
            </div>
        </#list>

    </div>
</@c.page>
