此版本开始完成第二步：处理请求的工作
上一个版本中clientHandler通过实例化HTTPRequest
完成了解析请求的工作，因此ClienHandler下一步操作
就是根据请求来进行处理
首先我们先对客户端请求一个页面这样的操作进行处理
因此本版本先准备一个页面。

实现：
1： 在当前项目下新建一个目录webapps
    该目录用于保存我们服务器中所有可以运行的webapp
    每个webapp是一个网络应用，里面应当包含页面，静态资源，逻辑代码等
    （就是一个网站需要的内容）
    然后每个webapp作为一个单独的子项目保存，目录名就是这个网络应用的
    名字
2： 在webapps下新建第一个网络应用myweb（新建一个名为myweb的子目录）

3：在myweb目录下新建第一个页面：index.html

4：ClientHandler完成第二步处理
   首先用户在浏览器的地址栏中输入路径，如：
   http://localhost:8088/myweb/index.html

   此时ClientHandler 第二步处理请求时通过request获取请求行中uri
   的值时应当得到的就是：
   /myweb/index.html

   然后实例化一个File，并且从webapps目录下再根据请求路径
   /myweb/index.html就应当可以找到我们创建的文件：
   ./webapps/myweb/index.html了。
   然后添加分支，判断文件是否存在并分别打桩。
