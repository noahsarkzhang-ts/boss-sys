<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="page" type="com.thinkgem.jeesite.common.db.Page" required="true" description="分页"%>
<%@ attribute name="pageSize" type="java.lang.Integer" required="false" description="每页大小" %>
<%@ attribute name="funcParam" type="java.lang.String" required="false" description="参数" %>
<%@ attribute name="pageNo" type="java.lang.Integer" required="false" description="当前页" %>
<%@ attribute name="simple" type="java.lang.Boolean" required="false" description="是否简单风格" %>

<c:if test="${empty pageSize}">
    <c:set var="pageSize" value="${page.pageSize}"/>
</c:if>

<c:set var="length" value="${page.length }"/>
<c:set var="begin" value="${page.pageNo - (length / 2)}"/>
<c:if test="${begin < page.first}">
	<c:set var="begin" value="${page.first}" />
</c:if>

<c:set var="end" value="${begin + length -1 }" />
<c:if test="${end >= page.last }">
	<c:set var="end" value="${page.last}" />
	<c:set var="begin" value="${end - length + 1 }"/>
	
	<c:if test="${begin < page.first}">
		<c:set var="begin" value="${page.first }" />
	</c:if>
	
</c:if>

<c:set var="pageNo" value="${page.pageNo }" />

<div class="pagination">
	<ul>
		<c:choose>
            <c:when test="${pageNo == page.first}">
               <li class="disabled"><a href="javascript:">&#171; 上一页</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="javascript:" onclick="page('${page.prev}','${pageSize}','${funcParam}');">&#171; 上一页</a></li>
            </c:otherwise>
        </c:choose>
        
        <c:if test="${begin > page.first}">
        	 <c:choose>
	        	<c:when test="${(page.first + page.slider) < begin}">
	        		<c:set var="frontBegin" value="${page.first + page.slider -1}"/> 
	        	</c:when>
	        	<c:otherwise>
	        		<c:set var="frontBegin" value="${begin - 1}" />
	        	</c:otherwise>
       	 	</c:choose>
       	 	
       	 	<c:forEach begin="${page.first}" end="${frontBegin}" var="e">
       	 		<li><a href="javascript:" onclick="page('${e}','${pageSize}','${funcParam}');">${e}</a></li>
       	 	</c:forEach>
       	 	
       	 	<c:if test="${(frontBegin + 1) < begin}">
       	 		<li class="disabled"><a href="javascript:">...</a></li>
       	 	</c:if>
       </c:if>
       
       <c:forEach begin="${begin}" end="${end}" var="e">
       		<c:choose>
       			<c:when test="${e != pageNo}">
       				<li><a href="javascript:" onclick="page('${e}','${pageSize}','${funcParam}');">${e}</a></li>
       			</c:when>
       			<c:otherwise>
       				<li class="active"><a href="javascript:">${e}</a></li>
       			</c:otherwise>
       		</c:choose>
       </c:forEach>
       
       <c:if test="${(page.last - end) > page.slider}">
       		<li class="disabled"><a href="javascript:">...</a></li>
       		<c:set var="end" value="${page.last - page.slider}"></c:set>
       </c:if> 
       
       <c:forEach begin="${end +1}" end="${page.last}" var="e">
       		<li><a href="javascript:" onclick="page('${e}','${pageSize}','${funcParam}');">${e}</a></li>
       </c:forEach>
       
       <c:choose>
       		<c:when test="${pageNo == page.last}">
       			<li class="disabled"><a href="javascript:">下一页 &#187;</a></li>
       		</c:when>
       		<c:otherwise>
       			<li><a href="javascript:" onclick="page('${page.next}','${pageSize}','${funcParam}');">&#187; 下一页</a></li>
       		</c:otherwise>
       </c:choose>
        
       <li class="disabled controls">
       	<a href="javascript:">当前
       		<input type="text" value="${pageNo}" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(this.value,'${pageSize}','${funcParam}');" onclick="this.select();"/> / 
       		<input type="text" value="${pageSize}" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page('${pageNo}',this.value,'${funcParam}');" onclick="this.select();"/> 条，
       		共  ${page.count} 条 ${(page.message!=null?page.message:"")}
       	</a>
      </li>
	</ul>
	<div style="clear:both;"></div>
</div>

