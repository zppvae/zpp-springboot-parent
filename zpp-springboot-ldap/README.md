## LDAP基础概念

https://blog.51cto.com/407711169/1439623

## springboot集成LDAP

### Linux搭建LDAP服务器

参考：https://www.cnblogs.com/viagraHero/p/7353880.html

### 初始化

```
dn: dc=my-domain,dc=com
objectClass: top
objectClass: domain

dn: ou=people,dc=my-domain,dc=com
objectclass: top
objectclass: organizationalUnit
ou: people
```

通过LDAP客户端或者`slapadd`命令导入。

### LDAP客户端

`Apache Directory Studio`