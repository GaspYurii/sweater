<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <h3>${userChannel.username}</h3>
    <#if !isCurrentUser>
        <#if isSubscriber>
            <a class="btn btn-info" href="/users/unsubscribe/${userChannel.id}">Unsubscribe</a>
        <#else>
            <a class="btn btn-info" href="/users/subscribe/${userChannel.id}">Subscribe</a>
        </#if>
    </#if>
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="card-body">
                    <div class="card-title">Subscriptions</div>
                    <div class="card-text">
                        <a href="/users/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card-body">
                    <div class="card-title">Subscribers</div>
                    <div class="card-text">
                        <a href="/users/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if isCurrentUser>
        <#if message??><#include "parts/messageEdit.ftlh" /></#if>
    </#if>
    <#include "parts/messageList.ftlh" />
</@c.page>