此版本继续完成第三步：响应客户端的工作
上个版本已经实现了响应客户端的工作，但是ClientHandler中
响应操作的代码由重复（响应正常资源与404操作）
因此，我们需要将响应的代码重用

与请求的设计思想一致，我们再设计一个类：
HttpResponse，使用当前类的每一个实例表示服务端给
客户端发送的响应内容

然后在处理请求的部分我们将要响应的内容设置到response
实例中，并在最后统一调用其flush方法，让response将内容
以标准的HTTP响应格式发送给客户端

实现：
1.在http包中新建一个HttpResponse
2.定义构造方法，以及flush方法
  flush方法用于将当前HTTPResponse对象的内容
  以一个标准的HTTP格式发送给客户端。
3.迁移ClientHandler中发送响应的代码到flush
  方法中
4.ClientHandler将要发送给客户端的资源设置到
HttpResponse中并最终调用flush方法完成响应。

这样一来，响应正确资源与404 都可以使用flush
方法来重用发送响应代码了


