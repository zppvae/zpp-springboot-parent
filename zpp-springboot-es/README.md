# 环境搭建

## 安装elasticsearch
 
从官网下载安装包：https://www.elastic.co/downloads/elasticsearch

中文社区：https://es.xiaoleilu.com/
 
```
# 解压
> tar -zxvf elasticsearch-6.4.3.tar.gz

# 修改elasticsearch.yml
network.host: 192.168.139.128
http.port: 9200

# 启动
# /usr/local/elasticsearch-6.4.3/bin
> ./elasticsearch
```

> 端口

`9300`，ES节点之间通讯使用（es集群）

`9200`， ES节点 和 外部 通讯使用，以及restful接口

http://192.168.139.128:9200/myindex/user/1

## 安装Kibana

```
> tar  -zxvf kibana-6.4.3-linux-x86_64.tar.gz

> vim config/kibana.yml

server.port: 5601
server.host: "192.168.139.128"
elasticsearch.url: "http:// 192.168.139.128:9200"

# 启动Kibana
> ./bin/kibana

# 访问
http://192.168.139.128:5601/app/kibana
```

# ES

## 倒排索引
`正排索引`是从文档到关键字的映射（已知文档求关键字）

`倒排索引`是从关键字到文档的映射（已知关键字求文档），可以使用`关键词`直接定位到文档内容

## 常见操作
   
```
##创建索引
PUT /myindex

##查询索引
GET /myindex

##添加文档
PUT /myindex/user/1
{
 "name":"test",
 "sex":1,
 "age":2
}

##查询文档
GET /myindex/user/1

##删除索引
DELETE /myindex

## 根据多个ID批量查询 
GET /myindex/user/_mget
{
 "ids":["1","2"]
}

## 查询年龄为年龄26岁
GET /myindex/user/_search?q=age:26

## 查询年龄30岁-60岁之间
GET /myindex/user/_search?q=age[30 TO 60]

## 查询年龄30岁-60岁之间 并且年龄降序、从0条数据到第1条数据
GET /myindex/user/_search?q=age[30 TO 60]&sort=age:desc&from=0&size=1

## 查询年龄30岁-60岁之间 并且年龄降序、从0条数据到第1条数据,展示name和age字段
GET /myindex/user/_search?q=age[30 TO 60]&sort=age:desc&from=0&size=1
&_source=name,age
```

## DSL语言查询
基于`post`请求的`json`格式查询

```
## 根据名称精确查询
## term是完全匹配
GET /testindex/user/_search
{
  "query": {
    "term": {
      "name": "zpp1"
    }
    
  }
  
}

## match模糊查询，根据查询字段的分词器进行分词查询
GET /testindex/user/_search
{
  "from": 0,
  "size": 2, 
  "query": {
    "match": {
        "name": "成"
      }
  }
}
```
## 分词器

中文分词器下载地址：https://github.com/medcl/elasticsearch-analysis-ik/releases
，下载与es对应的版本

> 安装中文分词器

- 载es的IK插件，命名改为ik插件，解压
- 上传到/usr/local/elasticsearch-6.4.3/plugins
- 重启elasticsearch即可

> 自定义扩展字典

```
> /usr/local/elasticsearch-6.4.3/plugins/ik/config
> mkdir custom
> vi custom/new_word.dic
//在里面增加热词，如：王者荣耀

> vi IKAnalyzer.cfg.xml
<!--用户可以在这里配置自己的扩展字典 -->
<entry key="ext_dict">custom/new_word.dic</entry>
```

## 文档映射
文档映射就是给文档中的字段指定字段类型、分词器。

### ES类型

> 基本类型

字符串：string，string类型包含 text 和 keyword。
keyword类型不能分词，Text类型可以分词查询

```
//创建文档类型并指定字段类型
POST /testindex/_mapping/user
{
  "user":{
    "properties":{
       "age":{
         "type":"integer"
       },
        "sex":{
         "type":"integer"
       },
       "name":{
         "type":"text",
         "analyzer":"ik_smart",//索引分词器
         "search_analyzer":"ik_smart" //搜索字段的值时，指定的分词器
       },
       "car":{
         "type":"keyword"
      
       }
    }
  }
  
}
```

## 集群

```
//查看索引设置
http://192.168.139.128:9200/testindex/_settings
//每一个主分片对应一个副分片
{
    "testindex": {
        "settings": {
            "index": {
                "creation_date": "1562247578570",
                "number_of_shards": "5",//主分片
                "number_of_replicas": "1",//副分片（1倍）
                "uuid": "SN8l8gXKQ4qzlAR55_hWHg",
                "version": {
                    "created": "6040399"
                },
                "provided_name": "testindex"
            }
        }
    }
}
```
### 名词

> Shards

代表索引分片，es可以把一个完整的索引分成多个分片，这样的好处是可以把一个大的索引拆分成多个，分布到不同的节点上。构成分布式搜索。分片的数量只能在索引创建前指定，并且索引创建后不能更改。

> replicas

代表索引副本，es可以设置多个索引的副本，副本的作用一是提高系统的容错性，当某个节点某个分片损坏或丢失时可以从副本中恢复。二是提高es的查询效率，es会自动对搜索请求进行负载均衡。

### 集群原理

每个索引会被分成多个分片shards进行存储，默认创建索引是分配5个分片进行存储。

每个分片都会分布式部署在多个不同的节点上进行部署，该分片成为primary shards。

每一个主分片为了实现高可用，都会有自己对应的备分片，主分片对应的备分片不能存放同一台服务器上。，主分片primary shards可以和其他replics shards存放在同一个node节点上。

> 索引的主分片primary shards定义好后，后面不能做修改。

当客户端发起创建document的时候，es需要确定这个document放在该index哪个shard上。这个过程就是数据路由。
```
//routing：文档id
路由算法：shard = hash(routing) % number_of_primary_shards
```
如果number_of_primary_shards在查询的时候取余发生的变化，会导致无法获取到数据

### 集群环境搭建
```
> vi elasticsearch.yml
# 服务器节点集群名称相同
cluster.name: myes
# 每个节点的名称不一样
node.name: node-1
# 节点IP地址
network.host: 192.168.139.128
# 集群节点
discovery.zen.ping.unicast.hosts: ["192.168.139.128", "192.168.139.129","192.168.139.130"]
discovery.zen.minimum_master_nodes: 1
```

**错误**

注意克隆data文件会导致数据不同步
`failed to send join request to master`
解决办法：删除每台服务器data文件