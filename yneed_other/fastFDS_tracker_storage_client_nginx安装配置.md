1、安装环境所需常用组件
	yum -y install zlib zlib-devel pcre pcre-devel gcc gcc-c++ openssl openssl-devel libevent libevent-devel perl unzip net-tools wget

2、安装libfastcommon
	(1)解压安装libfastcommon-1.0.7.tar.gz   
	./make.sh & ./make.sh install
	(2)复制libfastcommon.socp到/usr/lib 
	cp /usr/lib64/libfastcommon.socp   /usr/lib         
  
3、安装fastdfs
	(1)解压安装fastdfs-5.05.tar.gz
	./make.sh & ./make.sh install
	(2)复制配置文件conf/*到/etc/fdfs/
	cp /home/soft/fastdfs-5.05/conf/*  /etc/fdfs/
	
4、tracker服务
	(1)修改/etc/fdfs/tracker.conf配置文件
	base_path属性值为自建目录 /fastdfs/fdfs/tracker
	(2)启动tracker服务、重启  
	/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
	/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart
    (3)查看进场是否启动成功
    ps aux|grep tracker
   
5、storage服务
	(1)修改 /etc/fdfs/storage.conf配置文件
	base_path属性值为自建目录：/fastdfs/fdfs/storage
	group_name:访问图片路径前缀:fm
	storage_path0:图片存放实际路径:/fastdfs/fdfs/storage
	tracker_server:修改ip:port
	(2)启动storage服务、重启  
	/usr/bin/fdfs_storaged /etc/fdfs/storage.conf
	/usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart
	(3)查看进场是否启动成功
	ps aux|grep storage

6、client服务
	(1)修改 /etc/fdfs/client.conf配置文件
	base_path属性值为自建目录：/fastdfs/fdfs/client
	tracker_server:修改ip:port
	(2)测试配置是否成功
    /usr/bin/fdfs_test /etc/fdfs/client.conf upload 图片路径
	
7、NGINX按照与配置
	(1)解压安装fastdfs-nginx-module_v1.16.tar.gz
    /make.sh & ./make.sh install
	(2)配置fastdfs-nginx-module/src/config文件
    删除CORE_INCS和CORE_LIBS属性中的‘/local’路径
	(3)解压安装nginx-1.12.0.tar.gz
	--配置
		./configure \
		--prefix=/usr/local/nginx \
		--pid-path=/var/run/nginx/nginx.pid \
		--lock-path=/var/lock/nginx.lock \
		--error-log-path=/var/log/nginx/error.log \
		--http-log-path=/var/log/nginx/access.log \
		--with-http_gzip_static_module \
		--http-client-body-temp-path=/var/temp/nginx/client \
		--http-proxy-temp-path=/var/temp/nginx/proxy \
		--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
		--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
		--http-scgi-temp-path=/var/temp/nginx/scgi \
		--add-module=/home/soft/fastDFS/fastdfs-nginx-module/src
	--安装
		make & make install
    (4)拷贝mod_fastdfs.conf
	cp /home/soft/fastDFS/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs/
	(5)修改/etc/fdfs/mod_fastdfs.conf文件
	base_path: 存储日志  /fastdfs/fdfs/tmp
	tracker_server: tracker的ip:port
	group_name: fm
	store_path0: 与storage.conf属性值内容一样：：
	url_have_group_name: 是否生成组织的名字
	(5)nginx配置：/usr/local/nginx/conf/nginx.conf文件
	server{
		listen 88;
		server_name 192.168.92.128;
		location /fm/M00  {
			ngx_fastdfs_module;
		}
	}
	(6)启动nginx服务：/usr/local/nginx/sbin/
	./nginx -t --检查nginx有没有配置
	./nginx    
	./nginx  -s reload --重启
	
	 