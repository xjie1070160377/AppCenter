
<div class="page">
<li><a[#if page>1] href="${paging(1)}"[/#if]>首页</a></li>
[#if page>1]
<li><a href="${paging(page-1)}">上一页</a></li>
[/#if]
[#assign start=page-4/][#if start<1][#assign start=1/][/#if]
[#assign end=start+8/][#if end>pagedList.totalPages][#assign end=pagedList.totalPages/][/#if][#if end<1][#assign end=1/][/#if]
[#if end-start<8][#assign start=end-8/][/#if][#if start<1][#assign start=1/][/#if]
[#list start..end as p]
<li [#if page=p]class="thisbg"[/#if]><a[#if page!=p] href="${paging(p)}"[/#if]>${p}</a></li>
[/#list]
[#if page < pagedList.totalPages]
<li><a href="${paging(page+1)}">下一页</a></li>
[/#if]

<li><a[#if page<pagedList.totalPages] href="${paging(pagedList.totalPages)}"[/#if]>末页</a></li>
<div style="clear:both;"></div>
</div>

