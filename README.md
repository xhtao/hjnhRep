# hjnhRep
1.2018-5-15，仓库建立。

2.2018-8-8 Android 游戏开发 学习

3.2018-8-22 Html select的option根据数据库查询结果设置：
	<script>
		var a = ["aa","bb","cc"];
		$(document).ready(function () {
			//“input-test”：为select标签id
			var sub = document.getElementById("input-test");
			var set = function(){
				var frage = document.createDocumentFragment();
				for(var i=0,len=a.length;i<len;i++){
					var o = document.createElement("option");
					o.value = i;
					o.text = a[i];
					frage.appendChild(o);
				}
				sub.appendChild(frage);
			};
			set();
		})
	</script>
	
4.2018-8-30 Html select的option根据数据库查询结果(数组)设置：
	<select name="some">
		<?php
			$someArr = array(1=>'一', 2=>'二', 3=>'三'); //定义下拉表单元素数组
			foreach($someArr as $value => $showstr) {
			$select = $value == $some ? ' selected' : '';
			echo "<option value=\"$value\"$select>$showstr</option>";
			}
		?>
	</select>

5.2018-8-30 页面蒙版效果集锦
	(1)效果一
	<html>  
		<head>  
			<meta http-equiv="Content-Type" content="text/html; charset=gb2312">  
		</head>  
		<body style="margin:0;">  
			<div id="topCoverDiv" style="display:none;float:left;z-index:100;position:absolute;top:expression(this.offsetParent.scrollTop);left:expression(this.offsetParent.scrollLeft);width:100%;height:100%;filter:alpha(opacity=90);background-color:#888888;" oncontextmenu="return false;"/>  
			<div style="z-index:101;position:absolute;top:200px;left:200px;width:200px;height:200px;background-color:#FFFFFF;"><input type=button value="取消页面灰掉" onclick="disableBodyArea(false);" />
			</div>  
			</div>  
			<select><option >test</option></select>  
			<input type=button value="点击将页面灰掉" onclick="disableBodyArea(true);">   
			<br><br><br><br>  
			<br><br><br><br>  
			<select><option >test</option></select>  
			<br><br><br><br>  
			<br><br><br><br>  
			<select><option >test</option></select>  
			<select><option >test</option></select>  
		</body>  
	</html>  
	<script >
		function disableBodyArea(f){  
			document.getElementById('topCoverDiv').style.display = f? '':'none';  
			var allSelects=document.getElementsByTagName("select");  
			for(var i=0;i<allSelects.length;i++) {  
				allSelects[i].disabled=f;  
			}  
		}
	</script>
	(2)效果二
	<p>  
		<meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
	</p>  
	<script>  
		function msg(info){
			var p=document.createElement("DIV");  
			if (!info) var info='略海素材网：</br>http://www.luehai.com';  
			p.id="p";  
			p.style.position="absolute";  
			p.style.width=document.body.scrollWidth;  
			p.style.height=(document.body.offsetHeight>document.body.scrollHeight)?'100%':document.body.scrollHeight;  
			p.style.zIndex='998';  
			p.style.top='0px';  
			  p.style.left='0%';  
			p.style.backgroundColor="gray";  
			p.style.opacity='0.5';  
			p.style.filter="alpha(opacity=80)";  
			document.body.appendChild(p);  
			var p1=document.createElement("DIV");  
			var top=parseInt(parseInt(document.body.scrollHeight)*0.25)+document.body.scrollTop;  
			p1.style.position="absolute";  
			p1.style.width="300px";  
			p1.id="p1";  
			var left=Math.ceil(((document.body.scrollWidth)-parseInt(p1.style.width.replace('px','')))/2)+document.body.scrollLeft;  
			p1.style.height="200px";  
			p1.style.zIndex='999';  
			p1.style.top=top+'px';  
			  p1.style.left=left+'px';  
			p1.style.border="0px solid red";  
			var html="";  
			  html+="<center>"  
			  html+="<div class='p3' style='height:1px;overflow:hidden;background:red;width:294px;border-left:1px solid red;border-right:1px solid red;'></div>"  
			  html+="<div class='p2' style='height:1px;overflow:hidden;background:red;width:296px;border-left:1px solid red;border-right:1px solid red;'></div>"  
			  html+="<div class='p2' style='height:1px;overflow:hidden;background:red;width:298px;border-left:1px solid red;border-right:1px solid red;'></div>"  
			  html+="<div class='p1' style='height:20px;overflow:hidden;background:red;width:300px;border-left:1px solid red;border-right:1px solid red;color:#fff;font-size:9pt;font-weight:bold;text-align:left;'> ⊙ 略海提示</div>"  
			html+="<div id='c' style='height:150px;width:300px;background-color:#FEEACB;overflow:hidden;border-left:1px solid red;border-right:1px solid red;padding-top:40px;font-size:9pt;'>"+info+"<br><br><br>[ <a href='javascript:this.cancle()'>关闭</a> ]</div>"  
			  html+="<div class='p1' style='height:1px;overflow:hidden;background:#FEEACB;width:298px;border-left:1px solid red;border-right:1px solid red;'></div>"  
			  html+="<div class='p2' style='height:1px;overflow:hidden;background:#FEEACB;width:296px;border-left:1px solid red;border-right:1px solid red;'></div>"  
			  html+="<div class='p3' style='height:1px;overflow:hidden;background:red;width:294px;border-left:1px solid red;border-right:1px solid red'></div>"  
			  html+="</center>"  
			document.body.appendChild(p1);  
			p1.innerHTML=html;  
			var arr=document.getElementsByTagName("select");  
			var i=0;  
			while(i<arr.length){  
			  arr[i].style.visibility='hidden';  
			  i++;  
			}  
			this.cancle=function(){  
			document.body.removeChild(document.getElementById('p'));  
			document.body.removeChild(document.getElementById('p1'));  
			var arr=document.getElementsByTagName("select");  
			  var i=0;  
			  while(i<arr.length){  
			  arr[i].style.visibility='visible';  
			  i++;  
			  }  
			}  
		}
	</script>
	<center>
	<br>  
	<p><input type="button" onClick='msg()' value="页面提示效果" /></p></center>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
