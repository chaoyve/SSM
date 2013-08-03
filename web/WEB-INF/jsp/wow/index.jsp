<%@page pageEncoding="UTF-8"%>
<style type="text/css">
	td, th {
		text-align: center !important;
		vertical-align: middle !important;
	}

	caption{
		padding:10px;
	}
	
	td.info {
		background-color: rgba(73, 175, 205, 0.5) !important;
	}
</style>
<div class="span10">
	<h1>WOW每周击杀记录</h1>
	<br>
	<table class="table table-striped table-bordered table-hover" id="wow">
		<caption>
			<h4>
				<a href="<c:url value='/wow/back'/>" class="btn btn-primary pull-left" title="前一周">
					<i class="icon-step-backward icon-white"></i>
				</a>
				第${wowWeek}周 - <fmt:formatDate value="${wowDate}" pattern="yyyy年MM月"/>
				<a href="<c:url value='/wow/fore'/>" class="btn btn-primary pull-right" title="下一周">
					<i class="icon-step-forward icon-white"></i>
				</a>
			</h4>
		</caption>
		<thead>
			<tr>
				<th rowspan="2" class="text-center" width="150px">角色</th>
				<c:forEach items="${bosses }" var="boss">
					<th colspan="2">${boss.name }</th>
				</c:forEach>
			</tr>
			<tr>
				<c:forEach items="${bosses }" var="boss">
					<th width="40px">#</th>
					<th>装备</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rows }" var="row">
				<tr>
					<td>
						${row.role.name }&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-small editBtn" data-toggle="button">开始编辑</button>
					</td>
					<c:forEach items="${bosses }" var="boss">
						<td>
							<button class="btn btn-small bossBtn<c:if test='${row.hasBoss(boss.name)}'> active btn-info</c:if>"
								data-toggle="button" data-data="${row.role.id},${boss.name}" disabled="disabled">
								Done
							</button>
						</td>
						<td>
							<c:forEach items="${boss.equips }" var="equip">
								<button class="btn btn-small equipBtn<c:if test='${row.hasEquip(equip.id)}'> active btn-success</c:if>"
									data-toggle="button" data-data="${row.role.id},${equip.id}" disabled="disabled">
									${equip.name}
								</button>
							</c:forEach>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	var week = ${wowWeek};
</script>
<s:res type="js" files="wow" isLocal="true"/>
