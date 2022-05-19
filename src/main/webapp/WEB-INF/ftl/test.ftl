<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/resources/wangEditor.min.js"></script>
</head>
<body>
   <div id="divEditor" style="width: 800px;height: 600px"></div>
   <button id="btnRead">读取内容</button>
   <button id="btnWrite">写入内容</button>


<script>
  var E=window.wangEditor;

  var editor=new E("#divEditor");  //完成富文本编辑器初始化
  editor.create();  //创建富文本编辑器，显示在页面上
    document.getElementById("btnRead").onclick=function () {

           var content =editor.txt.html();

           alert(content);
    }


    document.getElementById("btnWrite").onclick=function () {

      var new_content =editor.txt.html("<h2 style='color: red'>new content</h2>");

  }

</script>


</body>
</html>